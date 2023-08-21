package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.InvestimentoCreateDTO;
import br.com.dbc.vemser.walletlife.dto.InvestimentoDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.entity.InvestimentoEntity;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import br.com.dbc.vemser.walletlife.repository.InvestimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvestimentoService {
    private InvestimentoRepository investimentoRepository;
    private UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public InvestimentoDTO create(InvestimentoCreateDTO investimento,  Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(idUsuario);
        if (usuarioById != null) {
            UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuarioById, UsuarioEntity.class);
            InvestimentoEntity entity = objectMapper.convertValue(investimento, InvestimentoEntity.class);
            entity.setUsuarioEntity(usuarioEntityConvertido);
            InvestimentoEntity investimentoEntityConvertido = investimentoRepository.save(entity);
            return convertToDTO(investimentoEntityConvertido);
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
    }

    public void remove(Integer id) {
        investimentoRepository.deleteById(id);
    }

    public InvestimentoDTO update(Integer id, InvestimentoCreateDTO investimento) throws RegraDeNegocioException  {
        try {
            Optional<InvestimentoEntity> investimentoRecuperado = investimentoRepository.findById(id);
            if (investimentoRecuperado.isEmpty()) {
                throw new RegraDeNegocioException("Investimento não encontrado");
            }
            InvestimentoEntity investimentoEntityDados = objectMapper.convertValue(investimento, InvestimentoEntity.class);
            InvestimentoEntity investimentoEntityExiste = investimentoRecuperado.get();

            BeanUtils.copyProperties(investimentoEntityDados, investimentoEntityExiste, "idInvestimento", "usuario");

            InvestimentoEntity investimentoEntityAtualizado = investimentoRepository.save(investimentoEntityExiste);
            InvestimentoDTO investimentoDTO = objectMapper.convertValue(investimentoEntityAtualizado, InvestimentoDTO.class);

            return investimentoDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public InvestimentoDTO findById(Integer idInvestimento) throws RegraDeNegocioException {
        Optional<InvestimentoEntity> investimento = investimentoRepository.findById(idInvestimento);
        if (investimento.isEmpty()){
            throw new RegraDeNegocioException("Investimento não encontrado");
        }
        InvestimentoEntity investimentoEntityExistente = investimento.get();
        InvestimentoDTO investimentoDTO = convertToDTO(investimentoEntityExistente);

        return investimentoDTO;
    }

    public List<InvestimentoDTO> findAll() {
        List<InvestimentoEntity> investimentoEntity = investimentoRepository.findAll();

        return convertToDTOList(investimentoEntity);
    }

    public List<InvestimentoDTO> findByUsuario(Integer idUsuario) throws RegraDeNegocioException   {
        UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(idUsuario);
        UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuarioById, UsuarioEntity.class);

        if (usuarioById != null){
            List<InvestimentoEntity> investimentoEntity = investimentoRepository.listInvestimentoListByIdUsuario(idUsuario);
            List<InvestimentoDTO> investimentoDTO = convertToDTOList(investimentoEntity);

            return investimentoDTO;
        }else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    public InvestimentoEntity convertToEntity(InvestimentoCreateDTO dto) {
        return objectMapper.convertValue(dto, InvestimentoEntity.class);
    }

    public InvestimentoDTO convertToDTO(InvestimentoEntity entity) {
        return objectMapper.convertValue(entity, InvestimentoDTO.class);
    }

    public List<InvestimentoDTO> convertToDTOList(List<InvestimentoEntity> investimentoEntities) {
        return investimentoEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
