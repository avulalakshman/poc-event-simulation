package com.heraizen.es.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.heraizen.es.domain.DimensionData;
import com.heraizen.es.domain.Service;
import com.heraizen.es.repo.ServiceRepo;

@org.springframework.stereotype.Service
public class M360ServiceConfigurerImpl implements M360ServiceConfigurer {

	@Autowired
	private ServiceRepo serviceRepo;
	
	@Override
	public Service getService(String svcName) {
		return serviceRepo.findBySvcName(svcName);
	}

	@Override
	public double calculatePrice(String svcName, List<DimensionData> eventData) {
		return 100;
	}

}
