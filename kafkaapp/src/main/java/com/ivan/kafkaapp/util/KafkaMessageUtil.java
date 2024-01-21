package com.ivan.kafkaapp.util;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

public class KafkaMessageUtil {
	public static String replacePlaceHolders(String template, Map<String, Object> params) {
		StringSubstitutor sub = new StringSubstitutor(params);
		String replacedString = sub.replace(template);
		return replacedString;
	}

}
