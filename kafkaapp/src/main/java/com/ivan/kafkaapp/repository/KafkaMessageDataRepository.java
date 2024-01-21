package com.ivan.kafkaapp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ivan.kafkaapp.entity.KafkaMessageData;

@Repository
public interface KafkaMessageDataRepository extends JpaRepository<KafkaMessageData, Long>{

}
