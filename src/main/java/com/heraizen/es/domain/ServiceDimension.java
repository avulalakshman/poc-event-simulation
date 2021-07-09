package com.heraizen.es.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.NaturalId;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class ServiceDimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NaturalId
    private String svcDimName;
    
    @Enumerated(EnumType.STRING)
    private DimensionType dimensionType;
  
    @OneToMany(mappedBy = "serviceDimension",cascade = CascadeType.ALL)
    private List<Picklist> picklist=new ArrayList<>();
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "svc_name",referencedColumnName = "svcName")
    private Service service;
        
   
}
