package com.ivan.kafkaapp.dto;

import java.time.LocalDateTime;

public class UserMessageData {
	public int templateId;
	public LocalDateTime createdDate;
	
	public UserMessageData(int templateId, LocalDateTime createdDate) {
		super();
		this.templateId = templateId;
		this.createdDate = createdDate;
	}
}
