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
import com.heraizen.es.domain.PicklistItem;
import com.heraizen.es.domain.RateTable;
import com.heraizen.es.domain.Service;
import com.heraizen.es.domain.ServiceDimension;
import com.heraizen.es.repo.RateTableRepo;
import com.heraizen.es.repo.ServiceDimensionRepo;
import com.heraizen.es.repo.ServiceRepo;
import com.heraizen.es.service.M360ServiceConfigurerImpl;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.Scanner;

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

    @Autowired
    private M360ServiceConfigurerImpl serviceConfigImpl;

    public static void main(String[] args) {
        SpringApplication.run(PocEventSimulationApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initData();

//        List<DimensionData> list = serviceEvent.getEventData();
//        double price = serviceConfigImpl.calculatePrice("HTTP APIs", list);
//        System.out.println("Estimated price :" + price);

        List<Service> services = serviceRepo.findAll();
        getDetailsOf(services);
    }

    private void initData() {
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

    }

    void getDetailsOf(List<Service> services) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean doContinue = true;
        do {
            int i = 0;
            System.out.println("\n\n Calculate Price for :\n\n");
            for (Service s : services) {
                System.out.println(String.format("\t\t %d) %s", i++,
                        s.getSvcName()));
            }
            System.out.print("\n Please enter your choice (Ctrl-C/-ve Num to Quit):");
            choice = scanner.nextInt();
            if (choice >= 0 && choice < services.size()) {
                List<DimensionData> dimensionData = getDimensionsInput(scanner, services.get(choice));
                System.out.println("User Estimation Input:\n " + dimensionData);
                try {
                    double price = serviceConfigImpl.calculatePrice(services.get(choice)
                            .getSvcName(), dimensionData);
                    System.out.println(String.format("Estimated price: %2f USD", price ));
                } catch(Throwable t) {
                    System.out.println("Error : " + t.getMessage());
                }
                System.out.print("Press any key to continue...");
                System.in.read();
            } else {
                doContinue = false;
            }
        } while (doContinue);

    }

    private List<DimensionData> getDimensionsInput(Scanner scanner, Service svc) {
        ArrayList<ServiceDimension> svcDimensions = new ArrayList<>(svc.getServiceDimensions());
        svcDimensions.sort(comparing(ServiceDimension::getOrdNo));
        List<DimensionData> userInput = new ArrayList<>();
        for (ServiceDimension sd : svcDimensions) {
            switch (sd.getDimensionType()) {
                case INPUT:
                    System.out.print("\n\t" + sd.getDescr()+ ":");
                    String val = scanner.next();
                    userInput.add(DimensionData.builder()
                            .dimensionName(sd.getSvcDimName())
                            .dimensionValue(val).build());
                    break;
                case PICKLIST:
                    System.out.println("\n\t" + sd.getDescr());
                    
                    int i = 0, choice;
                    for (PicklistItem item : sd.getPicklist()) {
                        System.out.println(String.format("\t\t %d) %s", i++, item.getName()));
                    }
                    System.out.print("Your choice :");
                    choice = scanner.nextInt();
                    if (choice >= 0 && choice < sd.getPicklist().size()) {
                        userInput.add(DimensionData.builder()
                                .dimensionName(sd.getSvcDimName())
                                .dimensionValue(sd.getPicklist()
                                        .get(choice).getName())
                                .build());
                    }
                    break;
                case DERIVED:
                    continue;
                default:
                    throw new RuntimeException("Unknown Dimension Type "+ sd.getDimensionType());
            }
        }
        return userInput;
    }

}
