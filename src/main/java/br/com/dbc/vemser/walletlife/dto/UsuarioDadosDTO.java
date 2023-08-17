package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.modelos.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDadosDTO {

    private Integer idUsuario;

    @NotNull
    @Size(min = 2, max = 255)
    @Schema(description = "Nome da Pessoa", required = true, example = "Monkey D. Luffy")
    private String nome;

    @NotNull
    @PastOrPresent
    @Schema(description = "Uma data de nascimento", required = true, example = "1998-11-21")
    private LocalDate dataNascimento;

    @NotNull
    @Size(max = 11)
    @Schema(description = "CPF de uma pessoa", required = true, example = "11111111111")
    private String cpf;

    @NotBlank
    @Size(min = 12)
    @Schema(description = "Email válido", required = true, example = "mail@mail.com")
    private String email;

    @NotBlank
    @Size(min = 5, max = 30)
    @Schema(description = "Senha de acesso", required = true, example = "senha123")
    private String senha;

    @Schema(description = "Lista de receitas", required = false, example = "[]")
    private List<ReceitaDTO> receitas = new ArrayList<>();

    @Schema(description = "Lista de despesas", required = false, example = "[]")
    private List<DespesaDTO> despesas = new ArrayList<>();

    @Schema(description = "Lista de investimentos", required = false, example = "[]")
    private List<InvestimentoDTO> investimentos = new ArrayList<>();

    public UsuarioDadosDTO(Usuario entity){
        BeanUtils.copyProperties(entity, this);
        this.receitas = entity.getReceitas().stream().map(
                receita -> new ReceitaDTO(receita)
        ).collect(Collectors.toList());

        this.despesas = entity.getDespesas().stream().map(
                despesa -> new DespesaDTO(despesa)
        ).collect(Collectors.toList());

        this.investimentos = entity.getInvestimentos().stream().map(
                investimento -> new InvestimentoDTO(investimento)
        ).collect(Collectors.toList());
    }

}
