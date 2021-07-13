/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service.pricecalc;

import com.heraizen.es.domain.RateTable;
import java.util.List;

/**
 *
 * @author Pradeepkm
 */
public class TieredPriceCalculator implements PriceCalculator{

    @Override
    public double calculatePrice(List<RateTable> rates, String dimensionVal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
