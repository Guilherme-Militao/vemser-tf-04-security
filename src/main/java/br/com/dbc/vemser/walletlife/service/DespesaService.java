package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.DespesaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.DespesaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.entity.DespesaEntity;
import br.com.dbc.vemser.walletlife.exceptions.EntidadeNaoEncontradaException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import br.com.dbc.vemser.walletlife.repository.DespesaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    public DespesaDTO adicionarDespesa(DespesaCreateDTO despesa) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(usuarioService.getIdLoggedUser());
        if (usuarioById != null) {
            UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuarioById, UsuarioEntity.class);
            DespesaEntity entity = objectMapper.convertValue(despesa, DespesaEntity.class);
            entity.setUsuarioEntity(usuarioEntityConvertido);
            DespesaEntity despesaEntityAdicionada = despesaRepository.save(entity);
            return convertToDTO(despesaEntityAdicionada);
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
    }

    // remoção
    public void removerDespesa(Integer idDespesa) {
        despesaRepository.deleteById(idDespesa);
    }

    // atualização de um objeto
    public DespesaDTO editarDespesa(Integer id, DespesaCreateDTO despesa) throws RegraDeNegocioException {
        DespesaEntity despesaEntity = despesaRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Usuario não encontrado"));

        despesaEntity.setTipo(despesa.getTipo());
        despesaEntity.setDescricao(despesa.getDescricao());
        despesaEntity.setValor(despesa.getValor());
        despesaEntity.setDescricao(despesa.getDescricao());
        despesaEntity.setDataPagamento(despesa.getDataPagamento());
        despesaEntity.setUsuarioEntity(usuarioService.findById(usuarioService.getIdLoggedUser()));


        return convertToDTO(despesaRepository.save(despesaEntity));
    }

    // leitura
    public List<DespesaDTO> listarDespesaByIdUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(idUsuario);
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioById, UsuarioEntity.class);

        if (usuarioEntity != null) {
            List<DespesaEntity> receitas = despesaRepository.findByUsuarioEntity(usuarioEntity);
            List<DespesaDTO> despesasDTO = this.convertToDTOList(receitas);
            return despesasDTO;
        } else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    public List<DespesaDTO> listar() {
        List<DespesaEntity> despesaEntities = despesaRepository.findAll();
        List<DespesaDTO> despesaDTOS = this.convertToDTOList(despesaEntities);
        return despesaDTOS;
    }

    public DespesaDTO findById(Integer id) throws RegraDeNegocioException, EntidadeNaoEncontradaException {
        Optional<DespesaEntity> despesaOP = despesaRepository.findById(id);
        if (despesaOP.isEmpty()) {
            throw new RegraDeNegocioException("Despesa não encontrada");
        }
        return convertToDTO(returnDespesaEntityById(id));
    }

    private DespesaDTO convertToDTO(DespesaEntity despesaEntity) {
        DespesaDTO despesaDTO = objectMapper.convertValue(despesaEntity, DespesaDTO.class);
        return despesaDTO;
    }

    private List<DespesaDTO> convertToDTOList(List<DespesaEntity> listaDespesaEntities) {
        return listaDespesaEntities.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    public DespesaEntity returnDespesaEntityById(Integer id) throws EntidadeNaoEncontradaException {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Despesa não encontrada"));
    }
}