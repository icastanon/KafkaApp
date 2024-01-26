package com.ivan.kafkaapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ivan.kafkaapp.dto.Response;
import com.ivan.kafkaapp.exception.TemplateNotFoundException;
import com.ivan.kafkaapp.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<Response<?>> handleException(Exception e){
		log.error("Error: " + e.getMessage());	
		return new ResponseEntity<>(Response.failureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler
	public ResponseEntity<Response<?>> handleTemplateNotFoundException(TemplateNotFoundException e){
		log.error("Error: " + e.getMessage());
		return new ResponseEntity<>(Response.failureResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Response<?>> handleUserNotFoundException(UserNotFoundException e){
		log.error("Error: " + e.getMessage());
		return new ResponseEntity<>(Response.failureResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
	}

}
