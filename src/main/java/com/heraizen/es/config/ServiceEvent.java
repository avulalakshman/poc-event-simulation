package com.heraizen.es.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.heraizen.es.domain.Event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "eventdata")
public class ServiceEvent {
	private List<Event> eventList;
}