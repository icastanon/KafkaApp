package com.ivan.kafkaapp.service.impl;

import java.util.Objects;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.entity.KafkaMessageData;
import com.ivan.kafkaapp.entity.KafkaMessageTemplate;
import com.ivan.kafkaapp.exception.TemplateNotFoundException;
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
		KafkaMessageTemplate template = kafkaRepo.findByTemplateId(request.getTemplateId());
		if(Objects.isNull(template)) {
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
		
		
		//send userId, timestamp, templateid, placeholders to db
		KafkaMessageData messageDataEntity = new KafkaMessageData(userId, request.getTemplateId());
		dataRepo.saveAndFlush(messageDataEntity);
	}
	
	@PreDestroy
	public void closeProducer() {
		kafkaProducer.close();
	}
	
}
