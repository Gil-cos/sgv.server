package br.com.gilberto.sgv.domain.vehicle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SGV_VEHICLE")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "vehicle_id_seq", sequenceName = "vehicle_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_id_seq")
	private Long id;
	
	@Column(name = "BRAND", nullable = false)
	private String brand;
	
	@Column(name = "MODEL", nullable = false)
	private String model;

	@Column(name = "LICENSE_PLATE", nullable = false)
	private String licensePlate;

	@Column(name = "NUMBER_OF_SEATS", nullable = false)
	private Integer numberOfSeats;
}
