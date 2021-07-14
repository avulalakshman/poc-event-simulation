package com.heraizen.es.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.heraizen.es.domain.Estimation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "estimationdata")
public class ServiceEvent {
	private Estimation estimation;	
}