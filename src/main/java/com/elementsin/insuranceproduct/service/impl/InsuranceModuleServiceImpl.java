package com.elementsin.insuranceproduct.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.elementsin.insuranceproduct.exception.ConstraintsViolationException;
import com.elementsin.insuranceproduct.exception.ModuleNotFoundException;
import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.PricesLog;
import com.elementsin.insuranceproduct.model.value.Money;
import com.elementsin.insuranceproduct.repository.InsuranceModuleRepository;
import com.elementsin.insuranceproduct.repository.PricesLogRepository;
import com.elementsin.insuranceproduct.service.InsuranceModuleService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InsuranceModuleServiceImpl implements InsuranceModuleService {
	private final InsuranceModuleRepository insuranceModuleRepository;
	private final PricesLogRepository pricesLogRepository;
	private final Executor executor;

	public InsuranceModuleServiceImpl(InsuranceModuleRepository insuranceModuleRepository
			, @Qualifier("fireAndForgetExecutor") Executor executor, PricesLogRepository pricesLogRepository) {
		this.insuranceModuleRepository = insuranceModuleRepository;
		this.pricesLogRepository = pricesLogRepository;
		this.executor = executor;
	}

	@Override
	public Money calculatePrice(String moduleId, Money selectedCoverage) throws ModuleNotFoundException {
		//step 1 retrieve the insurance module
		InsuranceModule retrievedInsuranceModule = retrieveInsuranceModule(moduleId);
		//step 2 validate against min/max coverage
		validateSelectedCoverage(selectedCoverage,retrievedInsuranceModule);
		//step 3 do calculation
		Money calculatedPrice = doCalculation(retrievedInsuranceModule,selectedCoverage);

		//step 4 save calculated price in fire & forget mode
		storeCalculatedPrice(retrievedInsuranceModule,selectedCoverage,calculatedPrice);

		return calculatedPrice;
	}

	@Override
	public List<InsuranceModule> findModules() {
		return this.insuranceModuleRepository.findAll();
	}

	private void validateSelectedCoverage(Money selectedCoverage,InsuranceModule insuranceModule){
		log.info("starting validating selected coverage amount: "+selectedCoverage.getAmount());
		BigDecimal minCoverageAmount = insuranceModule.getMinCoverage().getAmount();
		BigDecimal maxCoverageAmount = insuranceModule.getMaxCoverage().getAmount();

		if(selectedCoverage.getAmount().compareTo(minCoverageAmount) == -1
				|| selectedCoverage.getAmount().compareTo(maxCoverageAmount) == 1){
			log.warn("selected coverage is not valid and has to be withing max/min coverage boundary");
			throw new ConstraintsViolationException("selected coverage has to be between(inclusive) "+minCoverageAmount+" and "+maxCoverageAmount);
		}
		log.info("selected coverage with amount : "+selectedCoverage.getAmount()+" has successfully validated!!");
	}

	private InsuranceModule retrieveInsuranceModule(String moduleId) throws ModuleNotFoundException {
		log.info("starting retreiving insurance module with module id :"+moduleId);
		Optional<InsuranceModule> optionalInsuranceModule = this.insuranceModuleRepository.findById(moduleId);
		InsuranceModule insuranceModule = optionalInsuranceModule.orElseThrow(
				() -> {
					log.error("insurance module with id :"+moduleId+" is not found!!");
					return new ModuleNotFoundException("module id :" +moduleId+" is not exists");
				}
		);
		log.info("successfully retrieved insurance module with id: "+moduleId+" and module name : "+insuranceModule.getModuleName());
		return insuranceModule;
	}

	private Money doCalculation(InsuranceModule insuranceModule, Money selectedCoverage) {
		log.info("staring doing calculation for selected coverage : "+selectedCoverage.getAmount());
		BigDecimal resultAmount = selectedCoverage.getAmount().multiply(BigDecimal.valueOf(insuranceModule.getRisk()));
		log.info("price has been calculated successflly and it's equal to : "+resultAmount+" for selected coverage : "+selectedCoverage.getAmount()+" with "+insuranceModule.getModuleName()+" module!!!");
		return new Money(resultAmount);
	}

	private void storeCalculatedPrice(InsuranceModule retrievedInsuranceModule, Money selectedCoverage, Money calculatedPrice){
		// of course in bigger scale projects in distributed environments would be better to use messaging queueing solutions like Kafka
		executor.execute(() -> {
			log.info("started new thread to store calculated price "+calculatedPrice.getAmount()+" amount");
			PricesLog pricesLogRecord = PricesLog.builder()
					.calculatedPrice(calculatedPrice)
					.moduleName(retrievedInsuranceModule.getModuleName())
					.selectedCoverage(selectedCoverage)
					.build();
			pricesLogRepository.save(pricesLogRecord);
			log.info("price "+calculatedPrice.getAmount()+" has been stored successfully !");
		});
	}
}
