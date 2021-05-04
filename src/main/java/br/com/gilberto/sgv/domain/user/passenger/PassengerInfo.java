package br.com.gilberto.sgv.domain.user.passenger;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.gilberto.sgv.domain.institution.Institution;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SGV_PASSENGER_INFO")
public class PassengerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "passenger_info_id_seq", sequenceName = "passenger_info_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passenger_info_id_seq")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "INSTITUTION", nullable = false)
	private Institution institution;
	
	public PassengerInfo(final Institution institution) {
		this.institution = institution;
	}

	public PassengerInfo update(final Institution institution) {
		this.institution = institution;
		return this;
	}

}

