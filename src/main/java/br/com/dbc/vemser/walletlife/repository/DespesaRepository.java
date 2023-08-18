package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.entity.DespesaEntity;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity,Integer> {
   public List<DespesaEntity> findByUsuario(UsuarioEntity usuarioEntity);

    @Query(nativeQuery = true,value = "SELECT * FROM DESPESA WHERE ID_USUARIO = :idUsuario")
    List<DespesaEntity> listDespesaListByIdUsuario(Integer idUsuario);
}