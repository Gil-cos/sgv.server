package br.com.gilberto.sgv.domain.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.gilberto.sgv.domain.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "PHONE", nullable = false)
	private String phone;
	
	@Column(name = "CPF", nullable = false)
	private String cpf;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS", nullable = false)
	private Address address;


}
