package br.com.dbc.vemser.walletlife.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaCreateDTO {

    @NotNull(message = "O valor não pode ser nulo")
    @Schema(description = "Valor da receita", example = "1500.00", required = true)
    private Double valor;

    @NotNull(message = "A descrição não pode ser nula")
    @Size(min = 5, max = 30, message = "A descrição deve ter entre 5 e 30 caracteres")
    @Schema(description = "Descrição da receita", required = true, example = "Salário")
    private String descricao;

    @NotEmpty(message = "O banco não pode estar vazio")
    @Schema(description = "Nome do banco", required = true, example = "Banco do Brasil")
    private String banco;

    @NotEmpty(message = "A empresa não pode estar vazia")
    @Schema(description = "Nome da empresa", required = true, example = "Empresa XYZ")
    private String empresa;

}
