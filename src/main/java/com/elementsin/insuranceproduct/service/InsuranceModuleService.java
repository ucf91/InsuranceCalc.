package com.elementsin.insuranceproduct.service;

import java.util.List;

import com.elementsin.insuranceproduct.exception.ModuleNotFoundException;
import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.value.Money;

public interface InsuranceModuleService {
	Money calculatePrice(String moduleId, Money selectedCoverage) throws ModuleNotFoundException;

	List<InsuranceModule> findModules();
}
