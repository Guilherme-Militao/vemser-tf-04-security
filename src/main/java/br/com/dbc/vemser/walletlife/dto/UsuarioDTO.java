package br.com.dbc.vemser.walletlife.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends  UsuarioCreateDTO{
    private Integer idUsuario;
}
