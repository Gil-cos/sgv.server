package br.com.gilberto.sgv.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.gilberto.sgv.domain.route.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SGV_PRESENCE_CONFIRMATION")
public class PresenceConfirmation implements Serializable	 {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "presence_confirmation_id_seq", sequenceName = "presence_confirmation_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presence_confirmation_id_seq")
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "PASSENGER", nullable = false)
	private User passenger;
	
	@ManyToOne()
	@JoinColumn(name = "ROUTE", nullable = false)
	private Route route;
	
	@Column(name = "CONFIRMED", nullable = true)
	private boolean corfimed;
	
	public void confirmPresence() {
		this.corfimed = true;
	}
	
	public void declinePresence() {
		this.corfimed = false;
	}
	
}
