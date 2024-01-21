package com.ivan.kafkaapp.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kafka_message_data")
public class KafkaMessageData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="message_data_id")
	private int messageDataId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="kafka_message_template_id")
	private int templateId;
	
	@Column(name="created_date")
	@CreatedDate
	private LocalDateTime createdDate;

	public KafkaMessageData(String userId, int templateId) {
		super();
		this.userId = userId;
		this.templateId = templateId;
		this.createdDate = LocalDateTime.now();
	}

	public int getMessageDataId() {
		return messageDataId;
	}

	public void setMessageDataId(int messageDataId) {
		this.messageDataId = messageDataId;
	}

	public String getMessage() {
		return userId;
	}

	public void setMessage(String message) {
		this.userId = message;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	
}
