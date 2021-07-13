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
        rates.sort(Comparator.comparing(RateTable::getMinValue));
        double d = dimensionVal;
        double retPrice = 0;
        int i = 0;
        while (d > 0 && rates.size() > i ) {
            double tierPrice = 0;
            RateTable r = rates.get(i);
            if (r.getMaxValue() < d ) {
                tierPrice = r.getUnitPrice()*r.getMaxValue();
                log.info("Price From {} Until {} is {} ", 
                        r.getMinValue(), r.getMaxValue(), 
                        r.getUnitPrice(), tierPrice);
                retPrice += tierPrice;
                d -= r.getMaxValue() ;
            } else {
                tierPrice= r.getUnitPrice()*d;
                log.info("Price From {} Until {} is {} ", 
                        r.getMinValue(), dimensionVal, 
                        r.getUnitPrice(), tierPrice);
                d=0;
            }
            retPrice += tierPrice;
        }
        return retPrice;
    }
    
}
