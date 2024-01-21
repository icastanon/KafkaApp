package com.ivan.kafkaapp.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ivan.kafkaapp.constant.KafkaAppConstants;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaProducerConfig {
	@Value("${bootstrap.servers}")
	private String bootstrapServers;
	
	@Value("${key.serializer}")
	private String keySerializer;
	
	@Value("${value.serializer}")
	private String valueSerializer;
	
	@Bean
	public KafkaProducer<String, String> kafkaProducer() {
		log.info("Initializing kafka producer");
		Properties properties = new Properties();
		properties.put(KafkaAppConstants.BOOTSRAP_SERVERS, bootstrapServers);
		properties.put(KafkaAppConstants.KEY_SERIALIZER, keySerializer);
		properties.put(KafkaAppConstants.VALUE_SERIALIZER, valueSerializer);
		
		KafkaProducer<String, String> kafkaProd = new KafkaProducer<String, String>(properties);
		log.info("Completed initialization of kafka producer");
		return kafkaProd;
	}
}
