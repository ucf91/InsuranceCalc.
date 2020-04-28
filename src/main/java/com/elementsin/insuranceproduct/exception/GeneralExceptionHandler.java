package com.elementsin.insuranceproduct.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;


@EnableWebMvc
@ControllerAdvice
@Log4j2
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle validation exception exception.
	 * Since this exception being thrown by the validation framework and returned direct to the client
	 * It make sense to handle this exception and return my custom exception type in case there is any needed customization
	 *
	 * @param ex      the ex
	 * @param request the request
	 * @return the exception
	 */
	@ExceptionHandler(value = {ConstraintViolationException.class})
	protected Exception handleValidationException(final ConstraintViolationException ex, final WebRequest request) {
		log.error(ex);
		return new ConstraintsViolationException(ex.getMessage());
	}


}
