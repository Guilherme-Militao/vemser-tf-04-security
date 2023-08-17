package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.modelos.Despesa;
import br.com.dbc.vemser.walletlife.modelos.Investimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDTO extends DespesaCreateDTO{
    private int idDespesa;

    public DespesaDTO(Despesa entity){
        BeanUtils.copyProperties(entity, this);
    }

}
