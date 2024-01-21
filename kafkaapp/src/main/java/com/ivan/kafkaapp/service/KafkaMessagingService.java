package com.ivan.kafkaapp.service;

import com.ivan.kafkaapp.dto.MessagingRequest;

public interface KafkaMessagingService {
	public void sendMessage(String userId, MessagingRequest request);
}
