package com.heraizen.es.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.heraizen.es.domain.DimensionData;
import com.heraizen.es.domain.Service;
import com.heraizen.es.domain.ServiceDimension;
import com.heraizen.es.repo.ServiceRepo;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.scijava.parsington.Tokens;
import org.scijava.parsington.Variable;
import org.scijava.parsington.eval.Evaluator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class M360ServiceConfigurerImpl implements M360ServiceConfigurer {

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private DimensionPriceProvider priceProvider;

    @Override
    public Service getService(String svcName) {
        return serviceRepo.findBySvcName(svcName);
    }

    private Monetize360Evaluator getEvaluator() {
        final Monetize360Evaluator evaluator = new Monetize360Evaluator();
        evaluator.addFunction("price", (arg) -> {
            if (Tokens.isVariable(arg)) {
                Variable argVar = ((Variable) arg);
                String priceFor = argVar.getToken();
                Object argVal = evaluator.get(argVar);
                return priceProvider.getPriceFor(priceFor,Double.valueOf(argVal.toString()));
            }
            return null;
        });
        return evaluator;
    }
    
    private void evaluateDimensions(Service svc, 
            Map<String, String> evDataMap, Evaluator evaluator) {
        
        List<ServiceDimension> svcDimensions = new ArrayList<>(svc.getServiceDimensions());
        
        svcDimensions.sort((svcDim, svcDim1) -> {
            return svcDim.getDimensionType().compareTo(svcDim1.getDimensionType());
        });
        
        svcDimensions.forEach(sd -> {
            String varName = sd.getSvcDimName();

            switch (sd.getDimensionType()) {

                case INPUT:
                    evaluator.evaluate(varName + "=" + evDataMap.get(varName));
                    //i.e. assign varName as evaluated value
                    break;
                case PICKLIST:
                    String chosenVal = evDataMap.get(varName);
                    String actualVal = sd.getPicklist().stream()
                            .filter((pli) -> pli.getName().equals(chosenVal))
                            .findAny().get()
                            .getValue();
                    evaluator.evaluate(varName + "=" + actualVal);   
                    break;
                case DERIVED:
                    evaluator.evaluate(varName + "=" + sd.getValue());
                    break;
                default:
                    throw new RuntimeException("Unknown Dimension Type " + sd.getDimensionType());
            }
        });
    }
    
    @Override
    public double calculatePrice(String svcName, List<DimensionData> eventData) {
        //Prepare the data as a Map, easy to search
        Map<String, String> evDataMap = eventData.stream()
                .collect(Collectors.toMap(d -> d.getName(), d -> d.getValue()));
        
        //Create an evaluator
        Monetize360Evaluator evaluator = getEvaluator();
        
        //Now, Fetch the Service
        Service svc = getService(svcName);
        
        //Evaluate all Dimensions, with the user provided data
        evaluateDimensions(svc, evDataMap, evaluator);
        
        //Now finally Calculate the price
        Double price = (Double) evaluator.evaluate(svc.getSvcFormula());
        
        log.info("Evaluated Price for svc {}" + price);
        
        return price;
    }

}
