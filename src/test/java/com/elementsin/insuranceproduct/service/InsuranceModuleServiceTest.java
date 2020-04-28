package com.elementsin.insuranceproduct.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.elementsin.insuranceproduct.exception.ConstraintsViolationException;
import com.elementsin.insuranceproduct.exception.ModuleNotFoundException;
import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.value.Money;
import com.elementsin.insuranceproduct.repository.InsuranceModuleRepository;
import com.elementsin.insuranceproduct.repository.PricesLogRepository;
import com.elementsin.insuranceproduct.service.impl.InsuranceModuleServiceImpl;
import com.elementsin.insuranceproduct.util.TestUtils;

/**
 * The type Insurance module service test.
 * It's good idea to use MockitoJUnitRunner to so no nead to load spring container and make the test cases heavy
 */
@RunWith(MockitoJUnitRunner.class)
public class InsuranceModuleServiceTest {

	@Mock
	private InsuranceModuleRepository insuranceModuleRepository;

	private InsuranceModuleService insuranceModuleService;

	private PricesLogRepository pricesLogRepository;

	@Before
	public void setUp() {
		insuranceModuleService = new InsuranceModuleServiceImpl(insuranceModuleRepository, Executors.newCachedThreadPool(), pricesLogRepository);
	}

	@Test
	public void calculatePrice_happyScenario() throws ModuleNotFoundException {
		InsuranceModule insuranceModuleSample = TestUtils.generateSampleInsuranceModuleObject();
		given(insuranceModuleRepository.findById("123")).willReturn(Optional.of(insuranceModuleSample));

		Money selectedCoverage = TestUtils.generateMoney_1200();
		Money money = insuranceModuleService.calculatePrice("123",selectedCoverage);

		assertThat(money.getAmount().compareTo(selectedCoverage.getAmount().multiply(BigDecimal.valueOf(insuranceModuleSample.getRisk())))).isEqualTo(0);
	}

	@Test(expected = ConstraintsViolationException.class)
	public void calculatePrice_outOfBoundSelectedCoverage() throws ModuleNotFoundException{
		InsuranceModule insuranceModuleSample = TestUtils.generateSampleInsuranceModuleObject();
		given(insuranceModuleRepository.findById("123")).willReturn(Optional.of(insuranceModuleSample));

		Money selectedCoverage = TestUtils.generateMoney_500001();
		insuranceModuleService.calculatePrice("123",selectedCoverage);

	}

	@Test(expected = ModuleNotFoundException.class)
	public void calculatePrice_wrongModuleId() throws ModuleNotFoundException{
		given(insuranceModuleRepository.findById("123")).willReturn(Optional.empty());

		Money selectedCoverage = TestUtils.generateMoney_500001();
		insuranceModuleService.calculatePrice("123",selectedCoverage);
	}

	@Test
	public void testFindModules(){
		List<InsuranceModule> moduleList = new ArrayList<>();
		moduleList.add(TestUtils.generateSampleInsuranceModuleObject());
		given(insuranceModuleRepository.findAll()).willReturn(moduleList);

		List<InsuranceModule> returnedList = insuranceModuleService.findModules();
		assertThat(returnedList).isNotNull();

	}




}
