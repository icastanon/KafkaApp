package com.ivan.kafkaapp.service;

import com.ivan.kafkaapp.dto.LatestMessageResponse;
import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;

public interface KafkaMessagingService {
	public void sendMessage(String userId, MessagingRequest request);

	public UserMessageDataResponse getMessagesForUser(String userId);

	public UserMessageDataResponse getMessagesForTemplate(int templateId);

	public LatestMessageResponse getLatestMessage();
}
