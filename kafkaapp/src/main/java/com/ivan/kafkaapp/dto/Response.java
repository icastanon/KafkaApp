package com.ivan.kafkaapp.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -964000553650368654L;
	
	private ResponseStatus responseStatus;
	private String responseMsg;
	private T responseObj;
	private List<String> errors;
	
	public Response(ResponseStatus responseStatus, String responseMsg, T responseObj) {
		super();
		this.responseStatus = responseStatus;
		this.responseMsg = responseMsg;
		this.responseObj = responseObj;
	}
	
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public T getResponseObj() {
		return responseObj;
	}

	public void setResponseObj(T responseObj) {
		this.responseObj = responseObj;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public static <T> Response<T> successResponse(T respObject){
		return new Response<T>(ResponseStatus.SUCCESS, null, respObject);
	}
	
	public static <T> Response<T> failureResponse(String message){
		return new Response<T>(ResponseStatus.ERROR, message, null);
	}
	
	public static <T> Response<T> successResponse(String message){
		return new Response<T>(ResponseStatus.SUCCESS, message, null);
	}
	
	public static <T> Response<T> successResponse(T respObject, String message){
		return new Response<T>(ResponseStatus.SUCCESS, message, respObject);
	}
	
	public static <T> Response<T> failureResponse(T respObject, String message){
		return new Response<T>(ResponseStatus.ERROR, message, respObject);
	}
}
