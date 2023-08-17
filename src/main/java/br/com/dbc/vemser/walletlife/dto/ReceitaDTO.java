package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.modelos.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDTO extends ReceitaCreateDTO{
    private Integer id;

    public ReceitaDTO(Receita entity){
        BeanUtils.copyProperties(entity, this);
    }
}
