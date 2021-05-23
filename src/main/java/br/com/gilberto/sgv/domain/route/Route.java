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
import br.com.gilberto.sgv.domain.user.User;
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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private RouteStatus status;
	
	@ManyToOne()
	@JoinColumn(name = "DRIVER", nullable = false)
	private User driver;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SGV_ROUTE_PASSENGER", joinColumns = {
			@JoinColumn(name = "ID_ROUTE", nullable = false, referencedColumnName = "ID")}, inverseJoinColumns = {
			@JoinColumn(name = "ID_PASSENGER", nullable = false, referencedColumnName = "ID")})
	private final Set<User> passengers = new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SGV_CONFIRMED_PASSENGERS", joinColumns = {
			@JoinColumn(name = "ID_ROUTE", nullable = false, referencedColumnName = "ID")}, inverseJoinColumns = {
			@JoinColumn(name = "ID_PASSENGER", nullable = false, referencedColumnName = "ID")})
	private final Set<User> confirmedPassengers = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "INSTITUTION", nullable = false)
	private Institution institution;
	
	
	public Route(final String description, final Period period, final User driver, final Institution institution) {
		this.description = description;
		this.period = period;
		this.driver = driver;
		this.institution = institution;
		this.status = RouteStatus.STAND_BY;
	}	

	public Route update(final String description, final Period period, final User driver, final Institution institution) {
		this.description = description;
		this.period = period;
		this.driver = driver;
		this.institution = institution;
		return this;
	}

	public void addPassenger(final User passenger) {
		this.passengers.add(passenger);
	}
	
	public void removePassenger(final User passenger) {
		this.passengers.remove(passenger);
	}
	
	public void comfirmPassenger(final User passenger) {
		this.confirmedPassengers.add(passenger);
	}
	
	public void clearConfirmedPassengers() {
		this.passengers.clear();
	}

	public void updateStatus(final RouteStatus status) {
		this.status = status;		
	}
}
