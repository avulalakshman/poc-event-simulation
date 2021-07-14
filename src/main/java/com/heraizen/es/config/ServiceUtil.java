package com.heraizen.es.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.heraizen.es.domain.RateTable;
import com.heraizen.es.domain.Service;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "servicedata")
public class ServiceUtil {
	private List<Service> services;
	private List<RateTable> rateTables;
}
