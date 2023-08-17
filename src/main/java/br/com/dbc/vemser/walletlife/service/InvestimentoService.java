package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.InvestimentoCreateDTO;
import br.com.dbc.vemser.walletlife.dto.InvestimentoDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.modelos.Investimento;
import br.com.dbc.vemser.walletlife.modelos.Receita;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
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
        UsuarioDTO usuarioById = usuarioService.findById(idUsuario);
        if (usuarioById != null) {
            Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);
            Investimento entity = objectMapper.convertValue(investimento, Investimento.class);
            entity.setUsuario(usuarioConvertido);
            Investimento investimentoConvertido = investimentoRepository.save(entity);
            return convertToDTO(investimentoConvertido);
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
    }

    public void remove(Integer id) {
        investimentoRepository.deleteById(id);
    }

    public InvestimentoDTO update(Integer id, InvestimentoCreateDTO investimento) throws RegraDeNegocioException  {
        try {
            Optional<Investimento> investimentoRecuperado = investimentoRepository.findById(id);
            if (investimentoRecuperado.isEmpty()) {
                throw new RegraDeNegocioException("Investimento não encontrado");
            }
            Investimento investimentoDados = objectMapper.convertValue(investimento, Investimento.class);
            Investimento investimentoExiste = investimentoRecuperado.get();

            BeanUtils.copyProperties(investimentoDados, investimentoExiste, "idInvestimento", "usuario");

            Investimento investimentoAtualizado = investimentoRepository.save(investimentoExiste);
            InvestimentoDTO investimentoDTO = objectMapper.convertValue(investimentoAtualizado, InvestimentoDTO.class);

            return investimentoDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public InvestimentoDTO findById(Integer idInvestimento) throws RegraDeNegocioException {
        Optional<Investimento> investimento = investimentoRepository.findById(idInvestimento);
        if (investimento.isEmpty()){
            throw new RegraDeNegocioException("Investimento não encontrado");
        }
        Investimento investimentoExistente = investimento.get();
        InvestimentoDTO investimentoDTO = convertToDTO(investimentoExistente);

        return investimentoDTO;
    }

    public List<InvestimentoDTO> findAll() {
        List<Investimento> investimento = investimentoRepository.findAll();

        return convertToDTOList(investimento);
    }

    public List<InvestimentoDTO> findByUsuario(Integer idUsuario) throws RegraDeNegocioException   {
        UsuarioDTO usuarioById = usuarioService.findById(idUsuario);
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioById != null){
            List<Investimento> investimento = investimentoRepository.listInvestimentoListByIdUsuario(idUsuario);
            List<InvestimentoDTO> investimentoDTO = convertToDTOList(investimento);

            return investimentoDTO;
        }else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    public Investimento convertToEntity(InvestimentoCreateDTO dto) {
        return objectMapper.convertValue(dto, Investimento.class);
    }

    public InvestimentoDTO convertToDTO(Investimento entity) {
        return objectMapper.convertValue(entity, InvestimentoDTO.class);
    }

    public List<InvestimentoDTO> convertToDTOList(List<Investimento> investimentos) {
        return investimentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
