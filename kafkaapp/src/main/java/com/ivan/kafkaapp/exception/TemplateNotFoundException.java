package com.ivan.kafkaapp.exception;

public class TemplateNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TemplateNotFoundException(String message) {
		super(message);
	}

}
