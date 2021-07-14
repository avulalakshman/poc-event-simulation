/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service.pricecalc;

import com.heraizen.es.domain.RateTable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Pradeepkm
 */
@Slf4j
public class QuantityPriceCalculator implements PriceCalculator{

    @Override
    public double calculatePrice(List<RateTable> rates, double dimensionVal) {
        RateTable rate = rates.get(0);
        log.info("Doing a Quantity based pricing for {} of {} with val as {}", rate.getSvcDimName(), rate.getSvcName(), dimensionVal);
        double price = rate.getUnitPrice() * dimensionVal;
        log.info("Quantity based price for {} is {}", rate.getSvcDimName(), price);
        return price;
    }
    
}
