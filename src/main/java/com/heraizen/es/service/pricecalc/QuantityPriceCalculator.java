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
        log.info("Doing a Quantity based pricing ...");
        RateTable rate = rates.get(0);
        return rate.getUnitPrice() * dimensionVal;
    }
    
}
