package br.com.gilberto.sgv.domain.user.passanger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gilberto.sgv.domain.user.driver.DriverInfo;

public interface DriverInfoRepository extends JpaRepository<DriverInfo, Long> {

}
