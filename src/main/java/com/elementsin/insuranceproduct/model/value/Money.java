package com.elementsin.insuranceproduct.model.value;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.PersistenceConstructor;

import com.elementsin.insuranceproduct.exception.ConstraintsViolationException;
import lombok.Value;

/**
 * The type Money.
 * using Money value object making it easier in the future to add more features like supporting different currencies
 * In addition to wrapping the tedious validation of not null / gtOrEq
 */
@Value
public class Money {

	@NotNull(message = "amount can not be null!")
	@PositiveOrZero(message = "amount can not be negative")
	private BigDecimal amount;

	/**
	 * Of money.
	 * this static method makes it easier to create new money object from only double primitive value
	 * @param amount the amount
	 * @return the money
	 */
	public static Money of(double amount){
		return new Money(BigDecimal.valueOf(amount));
	}

	/**
	 * Instantiates a new Money.
	 *BigDecimal resultAmount = selectedCoverage.getAmount().multiply(BigDecimal.valueOf(insuranceModule.getRisk()));
	 * @param amount the amount
	 */
	@ConstructorProperties({"amount"})
	@PersistenceConstructor
	public Money(BigDecimal amount) throws ConstraintsViolationException {
		validateNonNull(amount);
		this.amount = amount;
	}

	private void validateNonNull(BigDecimal amount) throws ConstraintsViolationException {
		if(amount == null)
			throw new ConstraintsViolationException("money amount can not be null!");
	}
}
