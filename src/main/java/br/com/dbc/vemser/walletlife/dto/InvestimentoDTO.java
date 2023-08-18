package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.entity.InvestimentoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoDTO extends InvestimentoCreateDTO {
    private Integer idInvestimento;

    public InvestimentoDTO(InvestimentoEntity entity){
        BeanUtils.copyProperties(entity, this);
    }
}
