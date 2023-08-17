package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.modelos.Despesa;
import br.com.dbc.vemser.walletlife.modelos.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {
    @Query(nativeQuery = true,value = "SELECT * FROM INVESTIMENTO WHERE ID_USUARIO = :idUsuario")
    List<Investimento> listInvestimentoListByIdUsuario(Integer idUsuario);
}
