package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.entity.ReceitaEntity;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaEntity, Integer> {

    List<ReceitaEntity> findByUsuarioEntity(UsuarioEntity usuarioEntity);
    @Query("Select r From RECEITA r")
    Page<ReceitaEntity> findAll(Pageable pageable);

}
