package com.heraizen.es.domain;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DimensionData {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "id")
    private Event event;
    
    private String name;
    private double value;
}
