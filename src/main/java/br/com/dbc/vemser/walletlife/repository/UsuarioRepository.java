package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.dto.UsuarioComDespesaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioComInvestimentoDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioComReceitaDTO;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    @Query("""
        SELECT new br.com.dbc.vemser.walletlife.dto.UsuarioComDespesaDTO
        (u.idUsuario,u.nome,r.idDespesa,r.valor,r.descricao)
        FROM Usuario u
        JOIN u.despesaEntities r
         WHERE (:valor is null or r.valor > :valor)
        AND u.idUsuario=:idPessoa
    """)
    Page<UsuarioComDespesaDTO> findallUsuarioDespesa(@Param("valor") Double valor,Pageable pageable, Integer idPessoa);

    @Query("""
        SELECT NEW br.com.dbc.vemser.walletlife.dto.UsuarioComInvestimentoDTO
        (u.idUsuario, u.nome, i.idInvestimento, i.valor,i.descricao, i.corretora)
        FROM Usuario u
        JOIN u.investimentoEntities i
        WHERE (:corretora is null OR trim(upper(i.corretora)) = trim(upper(:corretora))) AND u.idUsuario=:idPessoa
    """)
    Page<UsuarioComInvestimentoDTO> findUsuariosByInvestimentoCorretora(String corretora, Pageable pageable, Integer idPessoa);

    @Query("""
        SELECT new br.com.dbc.vemser.walletlife.dto.UsuarioComReceitaDTO
        (u.idUsuario, u.nome, r.id, r.valor, r.descricao, r.banco, r.empresa)
        FROM Usuario u
        JOIN u.receitaEntities r
        WHERE (:valor is null or r.valor > :valor)
        AND u.idUsuario=:idPessoa
    """)
    Page<UsuarioComReceitaDTO> findallUsuarioReceita(@Param("valor") Double valor, Pageable pageable, Integer idPessoa);


    @Query("Select u From Usuario u where u.idUsuario = :idUsuario")
    Page<UsuarioEntity> findAllComOptional(@Param("idUsuario") Integer idUsuario, Pageable pageable);

    Optional<UsuarioEntity> findByLogin(String login);
}
