package br.com.gilberto.sgv.domain.route;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.user.DriverInfo;
import br.com.gilberto.sgv.domain.user.PassangerInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SGV_ROUTE")
public class Route  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "route_id_seq", sequenceName = "route_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_id_seq")
	private Long id;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PERIOD", nullable = false)
	private Period period;
	
	@ManyToOne()
	@JoinColumn(name = "DRIVER", nullable = false)
	private DriverInfo driver;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SGV_ROUTE_PASSANGER", joinColumns = {
			@JoinColumn(name = "ID_ROUTE", nullable = false, referencedColumnName = "ID")}, inverseJoinColumns = {
			@JoinColumn(name = "ID_PASSAGER", nullable = false, referencedColumnName = "ID")})
	private final Set<PassangerInfo> passangers = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "INSTITUTION", nullable = false)
	private Institution institution;
	
	
	public void addPassanger(final PassangerInfo passanger) {
		this.passangers.add(passanger);
		passanger.getRoutes().add(this);
	}
	
	public void removePassanger(final PassangerInfo passanger) {
		this.passangers.remove(passanger);
		passanger.getRoutes().remove(this);
	}
	
	
}
