package br.com.dbc.vemser.walletlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQUENCE")
    @SequenceGenerator(name = "USUARIO_SEQUENCE", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATANASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReceitaEntity> receitaEntities = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DespesaEntity> despesaEntities = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvestimentoEntity> investimentoEntities = new HashSet<>();

}