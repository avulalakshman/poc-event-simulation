/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.scijava.parsington.Tokens;
import org.scijava.parsington.Variable;
import org.scijava.parsington.eval.DefaultEvaluator;

/**
 *
 * @author Pradeepkm
 */
public class Monetize360Evaluator extends DefaultEvaluator {

    Map<String, Function<Object, Object>> functionTable = new HashMap<>();

    public Monetize360Evaluator() {
        super();
        this.functionTable.put("floor", floorFunction);
    }

    protected final Function<Object, Object> floorFunction = (arg) -> {
        Double retVal =null;
        if (Tokens.isVariable(arg)) {
            Variable argVar = ((Variable) arg);
            Object argVal = get(argVar);
            if (Tokens.isNumber(argVal)) {
                retVal = Math.floor((double) argVal);
            }
        } else if (Tokens.isNumber(arg)) {
            retVal = Math.floor((double) arg);
        }
        return retVal;
    };

    protected Object callM360Function(final String name, final Object b) {
        Object retVal = null;
        Function<Object, Object> fn = functionTable.get(name);
        if (fn != null) {
            retVal = fn.apply(b);
        }
        return retVal;
    }

    @Override
    public Object function(final Object a, final Object b) {
        Object result = super.function(a, b);
        if (result == null) {
            if (Tokens.isVariable(a)) {
                final String name = ((Variable) a).getToken();
                result = callM360Function(name, b);
            }
        }
        return result;
    }
    
    public void addFunction(String name, Function<Object, Object> func) {
        this.functionTable.put(name, func);
    }
}
