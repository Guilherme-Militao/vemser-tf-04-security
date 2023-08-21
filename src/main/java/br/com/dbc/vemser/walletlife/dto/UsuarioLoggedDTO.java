package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.entity.CargoEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UsuarioLoggedDTO {

    private Integer idUsuario;

    private String nome;

    private LocalDate dataNascimento;

    private String cpf;

    private String email;

    private String senha;

    private Set<CargoEntity> cargos;

    private String login;
}
