package com.elementsin.insuranceproduct.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elementsin.insuranceproduct.mapper.EntityMapper;
import com.elementsin.insuranceproduct.dto.InsuranceModuleDto;
import com.elementsin.insuranceproduct.exception.ModuleNotFoundException;
import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.value.Money;
import com.elementsin.insuranceproduct.service.InsuranceModuleService;

/**
 * The type Insurance module controller.
 */
@RestController
public class InsuranceModuleController {
	private final InsuranceModuleService insuranceModuleService;

	private final EntityMapper<InsuranceModule, InsuranceModuleDto> insuranceModuleMapper = insuranceModule -> {
		InsuranceModuleDto insuranceModuleDto = InsuranceModuleDto.builder()
				.id(insuranceModule.getId())
				.moduleName(insuranceModule.getModuleName())
				.risk(insuranceModule.getRisk())
				.minCoverage(insuranceModule.getMinCoverage())
				.maxCoverage(insuranceModule.getMaxCoverage())
				.build();
		return insuranceModuleDto;
	};

	/**
	 * Instantiates a new Insurance module controller.
	 *
	 * @param insuranceModuleService the insurance module service
	 */
	public InsuranceModuleController(InsuranceModuleService insuranceModuleService){
		this.insuranceModuleService = insuranceModuleService;
	}

	/**
	 * Calculate price money.
	 *
	 * @param moduleId         the module id
	 * @param selectedCoverage the selected coverage
	 * @return the money
	 * @throws ModuleNotFoundException the module not found exception
	 */
	// In case I have headers to return and more stuff to customize
	// I would go with ResponseEntity to have more control
	@PostMapping(value = "v1/modules/{moduleId}/price")
	public Money calculatePrice(@PathVariable String moduleId, @Valid @RequestBody Money selectedCoverage) throws ModuleNotFoundException {
		// now in this case it could be easier to make the client just send the amount number
		// but it's very likely there will be need for supporting currencies hence will be easier for the client
		// to adopt with the change
		return this.insuranceModuleService.calculatePrice(moduleId,selectedCoverage);
		// no need to create correspondant Dto for the money VO
	}

	@GetMapping(value = "v1/modules")
	public List<InsuranceModuleDto> findModules(){
		List<InsuranceModule> returnedModules = this.insuranceModuleService.findModules();
		return insuranceModuleMapper.mapList(returnedModules);
	}


}
