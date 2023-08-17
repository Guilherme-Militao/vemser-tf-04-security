package br.com.dbc.vemser.walletlife.modelos;

import br.com.dbc.vemser.walletlife.enumerators.TipoDespesaEReceita;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "INVESTIMENTO")
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVESTIMENTO")
    @SequenceGenerator(name = "SEQ_INVESTIMENTO", sequenceName = "SEQ_INVESTIMENTO", allocationSize = 1)
    @Column(name = "id_investimento")
    private Integer idInvestimento;

    @Column(name = "valor")
    private Double valor;

    @Size(min = 5, max = 30)
    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDespesaEReceita tipo;

    @Schema(description = "Nome da corretora do investimento", required = true)
    @Column(name = "corretora")
    protected String corretora;

    @Schema(description = "Data de início do investimento", required = true)
    @Column(name = "DATA_INICIAL")
    private LocalDate dataInicio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
}
