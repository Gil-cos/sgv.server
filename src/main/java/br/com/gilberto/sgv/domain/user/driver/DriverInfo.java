package br.com.gilberto.sgv.domain.user.driver;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.gilberto.sgv.domain.route.Route;
import br.com.gilberto.sgv.domain.vehicle.Vehicle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SGV_DRIVER_INFO")
public class DriverInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "driver_info_id_seq", sequenceName = "driver_info_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_info_id_seq")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "VEHICLE", nullable = false)
	private Vehicle vehicle;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "driver")
	private final Set<Route> routes = new HashSet<>();

	public DriverInfo(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public DriverInfo update(final Vehicle vehicle) {
		this.vehicle = vehicle;
		return this;
	}
	
}
