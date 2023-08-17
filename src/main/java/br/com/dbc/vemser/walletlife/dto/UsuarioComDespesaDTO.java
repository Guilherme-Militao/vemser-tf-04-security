package br.com.dbc.vemser.walletlife.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComDespesaDTO {

    private Integer idUsuario;
    @Schema(description = "Nome do usuario", required = true, example = "Monkey D. Luffy")
    private String nome;

    @Schema(description = "Uma despesa do usuario", required = true, example = "1")
    private Integer idDespesa;

    @Schema(description = "Valor da despesa do usuario", required = true, example = "1500")
    private Double valor;

    @Schema(description = "Descrição de uma despesa do usuario", required = true, example = "Conta de internet")
    private  String descricao;

    public UsuarioComDespesaDTO(Integer idUsuario, String nome, Integer idDespesa, Double valor, String descricao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.idDespesa = idDespesa;
        this.valor = valor;
        this.descricao = descricao;
    }
}

