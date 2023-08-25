package br.com.dbc.vemser.walletlife.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioSenhaDTO {
    @NotNull
    @NotBlank
    private String senha;
}
