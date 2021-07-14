package com.heraizen.es.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heraizen.es.domain.RateTable;

public interface RateTableRepo extends JpaRepository<RateTable,Long> {

	List<RateTable> findByServiceDimensionSvcDimName(String dimensionName);
	List<RateTable> findByServiceDimensionSvcDimNameAndServiceDimensionServiceSvcName(String dimensionName,String svcName);


}
