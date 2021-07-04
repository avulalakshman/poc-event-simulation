package com.heraizen.es.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class ServiceDimenstion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private DimenstionType dimenstionType;
    
    
    @OneToMany(mappedBy = "serviceDimenstion",cascade = CascadeType.ALL)
    private List<Picklist> picklist=new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "service_id",referencedColumnName = "id",nullable = false)
    private Service service;
    
    @OneToMany(mappedBy = "serviceDimenstion",cascade = CascadeType.ALL)
    private List<RateTable> rateTable=new ArrayList<>();
    
    public void addPicklist(Picklist picklist){
        this.picklist.add(picklist);
    }
    
    public void addRateTable(RateTable rateTable) {
    	this.rateTable.add(rateTable);
    }
}
