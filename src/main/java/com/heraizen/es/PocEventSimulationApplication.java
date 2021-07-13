package com.heraizen.es;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.heraizen.es.config.ServiceEvent;
import com.heraizen.es.config.ServiceUtil;
import com.heraizen.es.domain.DimensionData;
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
    private ServiceDimensionRepo serviceDimensionRepo;

    @Autowired
    private RateTableRepo rateTableRepo;

    @Autowired
    private ServiceEvent serviceEvent;

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

        List<RateTable> rateTables = serviceUtil.getRateTables();

        rateTables.forEach(r -> {
            ServiceDimension svcDimnsion = serviceDimensionRepo.findBySvcDimName(r.getSvcDimName());
            r.setServiceDimension(svcDimnsion);
        });

        rateTableRepo.saveAll(rateTables);

        List<DimensionData> list = serviceEvent.getEventData();
        
    }

}
