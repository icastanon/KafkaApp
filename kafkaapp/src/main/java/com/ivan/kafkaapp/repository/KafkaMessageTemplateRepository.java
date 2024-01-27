package com.ivan.kafkaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivan.kafkaapp.entity.KafkaMessageTemplate;

@Repository
public interface KafkaMessageTemplateRepository extends JpaRepository<KafkaMessageTemplate, Long> {
	
	public KafkaMessageTemplate findByTemplateId(int templateId);
	
	public List<KafkaMessageTemplate> findAll();
	
	public int deleteByTemplateId(int templateId);

}
