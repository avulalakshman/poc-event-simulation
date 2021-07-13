/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service.pricecalc;

import com.heraizen.es.domain.PriceMethod;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pradeepkm
 */
@Component
public class PriceCalculatorStrategyFactory {
    
    public PriceCalculator getPriceCalculatorFor(PriceMethod priceMethod) {
        switch( priceMethod ) {
            case QTY:
                return new QuantityPriceCalculator();
            case TIERED:
                return new TieredPriceCalculator();
            default:
                throw new RuntimeException("Unknown price method " + priceMethod + " Cant find pricing calculator");
        }
    }
}
