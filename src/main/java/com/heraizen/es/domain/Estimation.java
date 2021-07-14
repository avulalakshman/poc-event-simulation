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
import lombok.Data;

/**
 *
 * @author Pradeepkm
 */
@Data
public class Estimation {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)        
    private Long id;
    
    private String svcName;
    @OneToMany(mappedBy = "estimation", cascade = CascadeType.ALL)      
    private List<DimensionData> eventData;
}
