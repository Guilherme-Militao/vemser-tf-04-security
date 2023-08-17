package br.com.dbc.vemser.walletlife.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RECEITA")
public class Receita{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEITA_SEQ")
    @SequenceGenerator(name = "RECEITA_SEQ", sequenceName = "SEQ_RECEITA", allocationSize = 1)
    @Column(name = "id_receita")
    private Integer id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "banco")
    private String banco;

    @Column(name = "empresa")
    private String empresa;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
}
