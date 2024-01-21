package com.ivan.kafkaapp.dto;

import java.time.LocalDateTime;

public class LatestMessageResponse {
	public int templateId;
	public LocalDateTime createdDate;
	public String userId;
	
	public LatestMessageResponse(int templateId, LocalDateTime createdDate, String userId) {
		super();
		this.templateId = templateId;
		this.createdDate = createdDate;
		this.userId = userId;
	}
}
