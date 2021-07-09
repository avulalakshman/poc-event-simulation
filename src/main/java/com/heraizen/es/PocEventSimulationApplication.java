package com.heraizen.es;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.heraizen.es.config.ServiceEvent;
import com.heraizen.es.config.ServiceUtil;
import com.heraizen.es.domain.RateTable;
import com.heraizen.es.domain.Service;
import com.heraizen.es.domain.ServiceDimension;
import com.heraizen.es.repo.RateTableRepo;
import com.heraizen.es.repo.ServiceDimensionRepo;
import com.heraizen.es.repo.ServiceRepo;

@SpringBootApplication
public class PocEventSimulationApplication implements CommandLineRunner {

	@Autowired
	private ServiceUtil serviceUtil;

	@Autowired
	private ServiceRepo serviceRepo;
	
	@Autowired
	private ServiceEvent serviceEvent;
	
	@Autowired
	private ServiceDimensionRepo serviceDimensionRepo;
	
	@Autowired
	private RateTableRepo rateTableRepo;


	
	public static void main(String[] args) {
		SpringApplication.run(PocEventSimulationApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Service service = serviceUtil.getService();
		service.getServiceDimensions().forEach(d -> d.setService(service));
		service.getServiceDimensions().forEach(d -> {
			d.getPicklist().forEach(p -> {
				p.setServiceDimension(d);
				
			});
		});
    	serviceRepo.save(service);
    	
    	ServiceDimension svcDimnsion = serviceDimensionRepo.findBySvcDimName("request_unit");
    	List<RateTable> rateTables = serviceUtil.getRateTables();
    	if(svcDimnsion != null) {
    		rateTables.forEach(r->{
    			r.setServiceDimension(svcDimnsion);
    		});
    	}
    	rateTableRepo.saveAll(rateTables);
		System.out.println(serviceEvent.getEventList());

	}

}
