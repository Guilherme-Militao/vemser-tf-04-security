package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.enumerators.TipoDespesaEReceita;
import br.com.dbc.vemser.walletlife.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.walletlife.modelos.Despesa;
import br.com.dbc.vemser.walletlife.modelos.Investimento;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa,Integer> {
   public List<Despesa> findByUsuario(Usuario usuario);

    @Query(nativeQuery = true,value = "SELECT * FROM DESPESA WHERE ID_USUARIO = :idUsuario")
    List<Despesa> listDespesaListByIdUsuario(Integer idUsuario);
}