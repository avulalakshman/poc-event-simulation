package com.heraizen.es.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heraizen.es.domain.ServiceDimension;

public interface ServiceDimensionRepo extends JpaRepository<ServiceDimension,Long> {

		ServiceDimension findBySvcDimName(String svcDimName);
}
