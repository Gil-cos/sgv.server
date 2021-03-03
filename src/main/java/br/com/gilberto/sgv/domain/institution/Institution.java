package br.com.gilberto.sgv.domain.institution;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.gilberto.sgv.domain.address.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SGV_INSTITUTION")
public class Institution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "institution_id_seq", sequenceName = "institution_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "institution_id_seq")
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS", nullable = false)
	private Address address;
}
