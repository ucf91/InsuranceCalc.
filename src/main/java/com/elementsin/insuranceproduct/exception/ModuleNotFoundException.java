package com.elementsin.insuranceproduct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends Exception {
	static final long serialVersionUID = -3387516993224229948L;

	public ModuleNotFoundException(String message)
	{
		super(message);
	}

}
