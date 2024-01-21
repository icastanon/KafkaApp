package com.ivan.kafkaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivan.kafkaapp.entity.KafkaMessageTemplate;

@Repository
public interface KafkaMessageTemplateRepository extends JpaRepository<KafkaMessageTemplate, Long> {
	
	public KafkaMessageTemplate findByTemplateId(Integer templateId);

}
