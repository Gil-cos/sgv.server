package br.com.gilberto.sgv.domain.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gilberto.sgv.domain.address.Address;
import br.com.gilberto.sgv.domain.user.driver.DriverInfo;
import br.com.gilberto.sgv.domain.user.passenger.PassengerInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SGV_USER")
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PHONE", nullable = false)
	private String phone;

	@Column(name = "CPF", nullable = false)
	private String cpf;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS", nullable = true)
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PASSENGER_INFO", nullable = true)
	private PassengerInfo passengerInfo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DRIVER_INFO", nullable = true)
	private DriverInfo driverInfo;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", nullable = false)
	private Role role;

	public User(final String name, final String phone, final String cpf, final String email, final String password,
			final Role role) {
		this.name = name;
		this.phone = phone;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User update(final String name, final String phone, final String cpf, final Address address,
			final PassengerInfo passengerInfo, final DriverInfo driverInfo) {
		this.name = name;
		this.phone = phone;
		this.cpf = cpf;
		this.address = address;
		this.passengerInfo = passengerInfo;
		this.driverInfo = driverInfo;
		return this;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this.role);
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
