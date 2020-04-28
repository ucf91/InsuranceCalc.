package com.elementsin.insuranceproduct.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.elementsin.insuranceproduct.model.InsuranceModule;
import com.elementsin.insuranceproduct.model.PricesLog;
import com.elementsin.insuranceproduct.model.value.Money;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The type Test utils.
 * this class aims to reduce the repeated code in the testing codebase and make it DRY
 */
public class TestUtils {

	/**
	 * Generate sample insurance module object insurance module.
	 *
	 * @return the insurance module
	 */
	public static InsuranceModule generateSampleInsuranceModuleObject(){
		InsuranceModule insuranceModule = InsuranceModule.builder()
				.id("123")
				.risk(10000.0)
				.moduleName("Bikes")
				.minCoverage(generateMoney_0())
				.maxCoverage(generateMoney_500000())
				.build();
		return insuranceModule;
	}

	public static PricesLog genereateSamplePricesLogObject(){
		PricesLog pricesLog = PricesLog.builder()
				.calculatedPrice(Money.of(1000))
				.moduleName("Bike")
				.selectedCoverage(Money.of(600))
				.timestamp(LocalDateTime.now())
				.build();
		return pricesLog;
	}

	/**
	 * Generate money 0 money.
	 *
	 * @return the money
	 */
	public static Money generateMoney_0(){
		return new Money(BigDecimal.valueOf(0));
	}

	/**
	 * Generate money 500000 money.
	 *
	 * @return the money
	 */
	public static Money generateMoney_500000(){
		return new Money(BigDecimal.valueOf(500000));
	}

	public static Money generateMoney_500001(){
		return new Money(BigDecimal.valueOf(500001));
	}

	/**
	 * Generate money 1200 money.
	 *
	 * @return the money
	 */
	public static Money generateMoney_1200(){
		return new Money(BigDecimal.valueOf(1200));
	}

	/**
	 * Generate money null amount money.
	 *
	 * @return the money
	 * @throws IllegalAccessException the illegal access exception
	 */
	public static Money generateMoney_nullAmount() throws IllegalAccessException {
		Money money = new Money(BigDecimal.valueOf(1));
		FieldUtils.writeField(money, "amount", null, true);
		return money;
	}

	/**
	 * Generate money negative amount money.
	 *
	 * @return the money
	 */
	public static Money generateMoney_negativeAmount(){
		return new Money(BigDecimal.valueOf(-10));
	}


	/**
	 * As json string string.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
