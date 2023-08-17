package br.com.dbc.vemser.walletlife.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComReceitaDTO {

    private Integer idUsuario;

    @Schema(description = "Nome do usuario", required = true, example = "Monkey D. Luffy")
    private String nome;

    @Schema(description = "Uma receita do usuario", required = true, example = "1")
    private Integer idReceita;

    @Schema(description = "Valor da receita do usuario", required = true, example = "1500")
    private Double valor;

    @Schema(description = "Descrição de uma receita do usuario", required = true, example = "Ganhos com apostas")
    private  String descricao;

    @Schema(description = "Banco associao a rceita", required = true, example = "Banco XYZ")
    private String banco;

    public UsuarioComReceitaDTO(Integer idUsuario, String nome, Integer idReceita, Double valor, String descricao, String banco) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.idReceita = idReceita;
        this.valor = valor;
        this.descricao = descricao;
        this.banco = banco;
    }
}
