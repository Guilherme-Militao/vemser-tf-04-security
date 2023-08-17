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
@Entity(name = "DESPESA")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESPESA_SEQUENCE")
    @SequenceGenerator(name = "DESPESA_SEQUENCE", sequenceName = "seq_despesa", allocationSize = 1)
    @Column(name = "id_despesa")
    @NotNull
    private Integer idDespesa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDespesaEReceita tipo;

    @Schema(description = "Data do pagamento", required = true)
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;



    @Schema(description = "Valor da despesa", required = true)
    @Column(name = "valor")
    private Double valor;

    @Schema(description = "Descricao da despesa", required = true)
    @Size(min = 5, max = 30)
    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

}