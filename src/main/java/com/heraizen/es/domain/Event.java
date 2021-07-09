/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.heraizen.es.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Pradeepkm
 */
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)        
    Long id;
    
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)      
    List<DimensionData> eventData;
}
