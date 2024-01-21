package com.ivan.kafkaapp.dto;

import java.util.List;

public class UserMessageDataResponse {
	public List<UserMessageData> messages;
	public int count;
	
	public UserMessageDataResponse(List<UserMessageData> messages, int count) {
		super();
		this.messages = messages;
		this.count = count;
	}
}
