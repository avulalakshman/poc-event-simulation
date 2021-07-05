package com.heraizen.es.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class RateTable implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private PriceMethod priceMethod;
	private long maxValue;
	private long minValue;
	private double unitPrice;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "svc_dim_name", referencedColumnName = "svcDimName")
	private ServiceDimension serviceDimension;
}
