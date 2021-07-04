package com.heraizen.es.domain;

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
public class RateTable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private PriceMethod priceMethod;
	private long maxValue;
	private long minValue;
	private double unitPrice;
	@ManyToOne
	@JoinColumn(name = "service_dimenstion_id")
	private ServiceDimenstion serviceDimenstion;
}
