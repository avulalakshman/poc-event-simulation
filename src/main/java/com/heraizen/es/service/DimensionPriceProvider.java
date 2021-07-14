/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service;

import com.heraizen.es.domain.PriceMethod;
import com.heraizen.es.domain.RateTable;
import com.heraizen.es.domain.Service;
import com.heraizen.es.repo.RateTableRepo;
import com.heraizen.es.service.pricecalc.PriceCalculator;
import com.heraizen.es.service.pricecalc.PriceCalculatorStrategyFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pradeepkm
 */
@Component
@Slf4j
public class DimensionPriceProvider {

    @Autowired
    private RateTableRepo rateTableRepo;

    @Autowired
    private PriceCalculatorStrategyFactory priceCalcFactory;

    public double getPriceFor(Service svc, String dimensionName, double dimensionVal) {
        log.info("Getting rate list for {} from Rate Table with dimension value as {}", dimensionName, dimensionVal);
        List<RateTable> rateList = rateTableRepo.findByServiceDimensionSvcDimName(dimensionName);
        log.info("Rate Table has {} entries for {}", rateList.size(), dimensionName);
        if (rateList.isEmpty()) {
            String errMsg = String.format("Rate Table entry NOT found for {} ", dimensionName);
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        PriceMethod pricingMethod = rateList.get(0).getPriceMethod();
        log.info("Pricing method for {} is {}", dimensionName, pricingMethod);
        PriceCalculator priceCalc = priceCalcFactory.getPriceCalculatorFor(pricingMethod);
        return priceCalc.calculatePrice(rateList, dimensionVal);
    }
}
