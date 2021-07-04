package com.heraizen.es;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.heraizen.es.config.ServiceEvent;
import com.heraizen.es.config.ServiceUtil;
import com.heraizen.es.domain.Service;
import com.heraizen.es.repo.ServiceRepo;

@SpringBootApplication
public class PocEventSimulationApplication implements CommandLineRunner {

	@Autowired
	private ServiceUtil ServiceUtil;

	@Autowired
	private ServiceRepo serviceRepo;
	
	@Autowired
	private ServiceEvent serviceEvent;

	public static void main(String[] args) {
		SpringApplication.run(PocEventSimulationApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Service service = ServiceUtil.getService();
		service.getServiceDimenstions().forEach(d -> d.setService(service));
		service.getServiceDimenstions().forEach(d -> {
			d.getRateTable().stream().forEach(p->{
				p.setServiceDimenstion(d);
			});
			d.getPicklist().forEach(p -> {
				p.setServiceDimenstion(d);
			});
		});
		
		serviceRepo.save(service);
		
		System.out.println(serviceEvent.getEventList());

	}

}
