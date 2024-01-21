package com.ivan.kafkaapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kafka_message_template")
public class KafkaMessageTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="kafka_message_template_id")
	private int templateId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="message_name")
	private String messageName;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
