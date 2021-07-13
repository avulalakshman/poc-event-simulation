package com.heraizen.es.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NaturalId;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NaturalId
	private String svcName;
	private String description;
	private String svcFormula;

	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
	private List<ServiceDimension> serviceDimensions = new ArrayList<>();

	public void addDimenstion(ServiceDimension serviceDimension) {
		this.serviceDimensions.add(serviceDimension);
	}
}
