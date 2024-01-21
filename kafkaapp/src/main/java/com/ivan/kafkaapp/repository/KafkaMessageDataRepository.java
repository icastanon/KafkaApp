package com.ivan.kafkaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivan.kafkaapp.entity.KafkaMessageData;

@Repository
public interface KafkaMessageDataRepository extends JpaRepository<KafkaMessageData, Long>{
	public List<KafkaMessageData> findByTemplateId(Integer templateId);
	
	public List<KafkaMessageData> findByUserId(String userId);
}
