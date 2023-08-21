package br.com.dbc.vemser.walletlife.entity;

import java.util.Arrays;

public enum TipoCargo {
    ROLE_ADMIN(1),
    ROLE_USUARIO(2);
    private Integer tipo;

    TipoCargo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

}
