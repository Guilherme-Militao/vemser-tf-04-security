package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.enumerators.TipoDespesaEReceita;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaCreateDTO {
    @Schema(description ="Tipo de despesa" ,required = true, example = "FIXA")
    protected TipoDespesaEReceita tipo;

    @NotNull
    @Schema(description ="Valor da receita" ,required = true, example = "150.00")
    private Double valor;

    @NotNull
    @Size(min = 5, max = 30)
    @Schema(description ="Descrição da despesa" ,required = true, example = "Conta de internet")
    private String descricao;

    @NotNull
    private LocalDate dataPagamento;
}
