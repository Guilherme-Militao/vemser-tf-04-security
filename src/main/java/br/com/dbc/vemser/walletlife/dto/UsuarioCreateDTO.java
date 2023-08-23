package br.com.dbc.vemser.walletlife.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    @NotNull
    @Size(min = 2, max = 255)
    @Schema(description = "Nome da Pessoa", required = true, example = "Jane Doe")
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
    @Email
    @Size(min = 12)
    @Schema(description = "Email válido", required = true, example = "mail@mail.com")
    private String email;

    @NotBlank
    @Size(min = 5, max = 30)
    @Schema(description = "Senha de acesso", required = true, example = "senha123")
    private String senha;

}
