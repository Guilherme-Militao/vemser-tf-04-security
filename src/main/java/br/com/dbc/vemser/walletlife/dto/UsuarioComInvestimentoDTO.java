package br.com.dbc.vemser.walletlife.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComInvestimentoDTO {
    private Integer idUsuario;

    @Schema(description = "Nome do usuario", required = true, example = "Monkey D. Luffy")
    private String nome;

    @Schema(description = "Um investimento do usuario", required = true, example = "1")
    private Integer idInvestimento;

    @Schema(description = "Valor do investimento do usuario", required = true, example = "1500")
    private Double valor;

    @Schema(description = "Corretora do investimento", required = true, example = "Corretora ABC")
    private String corretora;

    public UsuarioComInvestimentoDTO(Integer idUsuario,
                                     String nome,
                                     Integer idInvestimento,
                                     Double valor,
                                     String corretora) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.idInvestimento = idInvestimento;
        this.valor = valor;
        this.corretora = corretora;
    }
}
