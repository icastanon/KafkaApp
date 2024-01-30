package com.ivan.kafkaapp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivan.kafkaapp.dto.LatestMessageResponse;
import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.dto.UserMessageData;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;
import com.ivan.kafkaapp.entity.KafkaMessageData;
import com.ivan.kafkaapp.entity.KafkaMessageTemplate;
import com.ivan.kafkaapp.exception.TemplateNotFoundException;
import com.ivan.kafkaapp.exception.UserNotFoundException;
import com.ivan.kafkaapp.repository.KafkaMessageDataRepository;
import com.ivan.kafkaapp.repository.KafkaMessageTemplateRepository;
import com.ivan.kafkaapp.service.KafkaMessagingService;
import com.ivan.kafkaapp.util.KafkaMessageUtil;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaMessagingServiceImpl implements KafkaMessagingService {
	@Value("${kafka.topic}")
	private String kafkaTopic;
	
	private KafkaProducer<String, String> kafkaProducer;
	private KafkaMessageTemplateRepository kafkaRepo;
	private KafkaMessageDataRepository dataRepo;
	
	@Autowired
	public KafkaMessagingServiceImpl(@Qualifier("kafkaProducer") KafkaProducer<String, String> kafkaProducer,
			KafkaMessageTemplateRepository kafkaRepo, KafkaMessageDataRepository dataRepo) {
		this.kafkaProducer = kafkaProducer;
		this.kafkaRepo = kafkaRepo;
		this.dataRepo = dataRepo;
	}

	@Override
	public void sendMessage(String userId, MessagingRequest request) {
		//get template from db
		Optional<KafkaMessageTemplate> result = kafkaRepo.findById(request.getTemplateId());
		KafkaMessageTemplate template = null;
		
		if(result.isPresent()) {
			template = result.get();
		}else {
			log.error("Template not found on database");
			throw new TemplateNotFoundException("Template not found for the given Template Id");
		}
		
		String templateMessage = template.getMessage();
		
		//replace values from request with template placeholders
		templateMessage = KafkaMessageUtil.replacePlaceHolders(templateMessage, request.getPlaceHolderValues());
		log.info("Message to be sent to kafka: " +  templateMessage);
		
		//send message to kafka topic
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(kafkaTopic, template.getMessageName(), templateMessage);
		kafkaProducer.send(producerRecord);
		
		
		//send userId, timestamp, templateid to db
		KafkaMessageData messageDataEntity = new KafkaMessageData(userId, request.getTemplateId());
		dataRepo.save(messageDataEntity);
	}

	@Override
	public UserMessageDataResponse getMessagesForUser(String userId) {
		//getting messages from db
		List<KafkaMessageData> messageEntities = dataRepo.findByUserId(userId);
		List<UserMessageData> messages = new ArrayList<>();
		
		if(messageEntities.size() <= 0) {
			log.error("No messages for the user id");
			throw new UserNotFoundException("The given user has not sent any messages");
		}
		
		//constructing response
		for(KafkaMessageData data : messageEntities) {
			UserMessageData messageData = new UserMessageData(data.getTemplateId(), data.getCreatedDate());
			messages.add(messageData);
		}
		
		UserMessageDataResponse response = new UserMessageDataResponse(messages, messages.size());
		return response;
	}

	@Override
	public UserMessageDataResponse getMessagesForTemplate(int templateId) {
		Optional<KafkaMessageTemplate> result = kafkaRepo.findById(templateId);
		
		if(!result.isPresent()) {
			log.error("Template not found on database");
			throw new TemplateNotFoundException("Template not found for the given Template Id");
		}
		//getting messages from db
		List<KafkaMessageData> messageEntities = dataRepo.findByTemplateId(templateId);
		List<UserMessageData> messages = new ArrayList<>();
		
		if(messageEntities.size() <= 0) {
			log.error("No messages for the template id");
			throw new TemplateNotFoundException("No messages have been sent using the given Template Id");
		}
		
		//constructing response
		for(KafkaMessageData data : messageEntities) {
			UserMessageData messageData = new UserMessageData(data.getTemplateId(), data.getCreatedDate());
			messages.add(messageData);
		}
		
		UserMessageDataResponse response = new UserMessageDataResponse(messages, messages.size());
		return response;
	}
	
	@Override
	public LatestMessageResponse getLatestMessage() {
		KafkaMessageData latestMessage = dataRepo.findTopByOrderByCreatedDateDesc();
		
		LatestMessageResponse response = new LatestMessageResponse(latestMessage.getTemplateId(), latestMessage.getCreatedDate(), latestMessage.getUserId());
		
		return response;
	}
	
	@Override
	public List<KafkaMessageTemplate> getAllTemplates() {
		List<KafkaMessageTemplate> allTemplates = kafkaRepo.findAll();
		return allTemplates;
	}
	

	@Override
	public KafkaMessageTemplate getTemplate(int templateId) {
		Optional<KafkaMessageTemplate> result = kafkaRepo.findById(templateId);
		KafkaMessageTemplate template = null;
		
		if(result.isPresent()) {
			template = result.get();
		}else {
			log.error("Template not found on database");
			throw new TemplateNotFoundException("Template not found for the given Template Id");
		}
		
		return template;
	}
	
	@Override
	public KafkaMessageTemplate saveTemplate(KafkaMessageTemplate template, String userId) {
		template.setTemplateId(null);
		template.setCreatedBy(userId);
		template.setCreatedDate(LocalDateTime.now());
		template.setLastEditedBy(null);
		template.setLastEditedBy(null);
		return kafkaRepo.save(template);
	}
	
	@Override
	public KafkaMessageTemplate updateTemplate(KafkaMessageTemplate updateTemplateRequest, String userId) {
		Optional<KafkaMessageTemplate> result = kafkaRepo.findById(updateTemplateRequest.getTemplateId());
		KafkaMessageTemplate template = null;
		
		if(result.isPresent()) {
			template = result.get();
		}else {
			log.error("Template not found on database");
			throw new TemplateNotFoundException("Template not found for the given Template Id");
		}
		
		updateTemplateRequest.setLastEditedBy(userId);
		updateTemplateRequest.setLastEditedDate(LocalDateTime.now());
		updateTemplateRequest.setCreatedBy(template.getCreatedBy());
		updateTemplateRequest.setCreatedDate(template.getCreatedDate());
		
		return kafkaRepo.save(updateTemplateRequest);
	}
	
	@Transactional
	@Override
	public void deleteTemplate(int templateId) {
		Optional<KafkaMessageTemplate> result = kafkaRepo.findById(templateId);
		if(!result.isPresent()) {
			log.error("Template not found on database");
			throw new TemplateNotFoundException("Template not found for the given Template Id");
		}
		
		kafkaRepo.deleteById(templateId);
		
	}

	@PreDestroy
	public void closeProducer() {
		kafkaProducer.close();
	}
	
}
