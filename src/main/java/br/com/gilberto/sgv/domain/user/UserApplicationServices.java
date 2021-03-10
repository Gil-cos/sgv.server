package br.com.gilberto.sgv.domain.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationServices {

	private final UserRepository userRepository;

	@Transactional
	public User save(final User user) {
		return userRepository.save(user);
	}

	public Optional<User> findById(final Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public User update(final User user) {
		
		return userRepository.save(user);
	}

}
