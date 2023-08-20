package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
}
