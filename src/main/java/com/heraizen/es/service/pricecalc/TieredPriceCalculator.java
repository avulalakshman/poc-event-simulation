/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service.pricecalc;

import com.heraizen.es.domain.RateTable;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Pradeepkm
 */
@Slf4j
public class TieredPriceCalculator implements PriceCalculator{

    @Override
    public double calculatePrice(List<RateTable> rates, double dimensionVal) {
        log.info("Doing a Tiered based pricing with {} Tiers ", rates.size());
        rates.sort(Comparator.comparing(RateTable::getMinValue));
        double d = dimensionVal;
        double retPrice = 0;
        int i = 0;
        do  {
            double tierPrice;
            RateTable r = rates.get(i);
            if (r.getMaxValue() < d ) {
                tierPrice = r.getUnitPrice()*r.getMaxValue();
                log.info("Price From {} Until {} with UnitPrice: {} Tier Price: {}", 
                        r.getMinValue(), r.getMaxValue(), r.getUnitPrice(), tierPrice);
                d -= r.getMaxValue() ;
            } else {
                tierPrice= r.getUnitPrice()*d;
                log.info("Price From {} Until {} with Unit Price: {} Tier Price: {}", 
                        r.getMinValue(), dimensionVal, r.getUnitPrice(), tierPrice);
                d=0;
            }
            retPrice += tierPrice;
        } while (d > 0 && rates.size() > i++ );
        return retPrice;
    }
    
}
