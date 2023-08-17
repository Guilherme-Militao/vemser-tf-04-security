package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.enumerators.TipoDespesaEReceita;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para criar um novo Investimento")
public class InvestimentoCreateDTO {
    @NotNull
    @Schema(description = "Tipo de investimento", example = "FIXA", required = true)
    protected TipoDespesaEReceita tipo;

    @NotNull
    @Schema(description = "Valor do investimento", example = "1500.00", required = true)
    private Double valor;

    @NotNull
    @Size(min = 5, max = 30)
    @Schema(description = "Descrição do investimento", required = true, example = "Investimento em ações")
    private String descricao;

    @NotEmpty
    @Schema(description = "Nome da corretora", required = true, example = "Corretora XYZ")
    private String corretora;

    @NotNull
    @Schema(description = "Data de início do investimento", required = true, example = "2023-08-09")
    private LocalDate dataInicio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
}
