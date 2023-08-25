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

    public InvestimentoDTO create(InvestimentoCreateDTO investimento) {
        UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(usuarioService.getIdLoggedUser());
        UsuarioEntity usuario = objectMapper.convertValue(usuarioById, UsuarioEntity.class);

        InvestimentoEntity novoInvestimento = objectMapper.convertValue(investimento, InvestimentoEntity.class);
        novoInvestimento.setUsuarioEntity(usuario);

        InvestimentoEntity investimentoEntityConvertido = investimentoRepository.save(novoInvestimento);

        return convertToDTO(investimentoEntityConvertido);
    }

    public void remove(Integer id) {
        investimentoRepository.deleteById(id);
    }

    public InvestimentoDTO update(Integer id, InvestimentoCreateDTO investimento) throws RegraDeNegocioException {
        Optional<InvestimentoEntity> investimentoBuscado = investimentoRepository.findById(id);
        if (investimentoBuscado.isEmpty()) {
            throw new RegraDeNegocioException("Investimento não encontrado!");
        }

        Integer userId = usuarioService.getIdLoggedUser();
        InvestimentoEntity investimentoEntity = investimentoBuscado.get();
        if (!investimentoEntity.getUsuarioEntity().getIdUsuario().equals(userId)) {
            throw new RegraDeNegocioException("ID de investimento inválido.");
        }

        InvestimentoEntity investimentoDados = objectMapper.convertValue(investimento, InvestimentoEntity.class);
        investimentoEntity.setTipo(investimentoDados.getTipo());
        investimentoEntity.setValor(investimentoDados.getValor());
        investimentoEntity.setDescricao(investimentoDados.getDescricao());
        investimentoEntity.setCorretora(investimentoDados.getCorretora());
        investimentoEntity.setDataInicio(investimentoDados.getDataInicio());

        InvestimentoEntity investimentoAtualizado = investimentoRepository.save(investimentoEntity);

        return convertToDTO(investimentoAtualizado);
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
