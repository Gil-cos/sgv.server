package br.com.gilberto.sgv.domain.address;

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
@Table(name = "SGV_ADDRESS")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
	private Long id;
	
	@Column(name = "CEP", nullable = false)
	private String cep;
	
	@Column(name = "STREET", nullable = false)
	private String street;
	
	@Column(name = "NEIGHBORHOOD", nullable = false)
	private String neighborhood;
	
	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Column(name = "NUMBER", nullable = false)
	private String number;
}
