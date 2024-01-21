package com.ivan.kafkaapp.dto;

import java.util.Map;

public class MessagingRequest {
	private Integer templateId;
	private Map<String, Object> placeHolderValues;
	
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public Map<String, Object> getPlaceHolderValues() {
		return placeHolderValues;
	}
	public void setPlaceHolderValues(Map<String, Object> placeHolderValues) {
		this.placeHolderValues = placeHolderValues;
	}

}
