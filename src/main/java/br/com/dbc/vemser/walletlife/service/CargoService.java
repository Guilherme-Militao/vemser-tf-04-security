package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.entity.CargoEntity;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoEntity getByid(Integer idCargo) throws RegraDeNegocioException{
        CargoEntity cargoEntity = findByid(idCargo);
        return cargoEntity;
    }
    public CargoEntity findByid(Integer idCargo) throws RegraDeNegocioException {
        return cargoRepository.findById(idCargo).orElseThrow(()-> new RegraDeNegocioException("Cargo nao encontrado"));
    }
}
