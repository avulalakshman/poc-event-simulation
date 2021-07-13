/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service;

import com.heraizen.es.domain.PriceMethod;
import com.heraizen.es.domain.RateTable;
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
    RateTableRepo rateTableRepo;

    @Autowired
    PriceCalculatorStrategyFactory priceCalcFactory;

    public double getPriceFor(String dimensionName, double dimentionVal) {

        //get the rateTableItems for this dimentsion, it will be a list (for ex: Tiers)...findByDimensionName method on the RateTableRepository
        List<RateTable> rateList = rateTableRepo.findAll()
                ;
        if (rateList.isEmpty()) {
            String errMsg = String.format("Rate Table entry NOT found for {} ", dimensionName);
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        PriceMethod pricingMethod = rateList.get(0).getPriceMethod();
        PriceCalculator priceCalc = priceCalcFactory.getPriceCalculatorFor(pricingMethod);
        return priceCalc.calculatePrice(rateList, dimensionName);
    }
}
