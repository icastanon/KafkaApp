package com.ivan.kafkaapp.service;

import java.util.List;

import com.ivan.kafkaapp.dto.LatestMessageResponse;
import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;
import com.ivan.kafkaapp.entity.KafkaMessageTemplate;

public interface KafkaMessagingService {
	public void sendMessage(String userId, MessagingRequest request);

	public UserMessageDataResponse getMessagesForUser(String userId);

	public UserMessageDataResponse getMessagesForTemplate(int templateId);
	
	public List<KafkaMessageTemplate> getAllTemplates();

	public LatestMessageResponse getLatestMessage();

	public KafkaMessageTemplate getTemplate(int templateId);

	public KafkaMessageTemplate saveTemplate(KafkaMessageTemplate template, String userId);

	public KafkaMessageTemplate updateTemplate(KafkaMessageTemplate updateTemplateRequest, String userId);

	public void deleteTemplate(int templateId);
}
