package com.ivan.kafkaapp.service;

import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;

public interface KafkaMessagingService {
	public void sendMessage(String userId, MessagingRequest request);

	public UserMessageDataResponse getMessagesForUser(String userId);
}
