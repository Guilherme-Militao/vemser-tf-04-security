package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.ReceitaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.ReceitaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.entity.ReceitaEntity;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import br.com.dbc.vemser.walletlife.repository.ReceitaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    public ReceitaDTO create(ReceitaCreateDTO receita) {
        UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(usuarioService.getIdLoggedUser());
        try{
       if (usuarioById == null) {
           throw new RegraDeNegocioException("Usuário não encontrado");
       }
        UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuarioById, UsuarioEntity.class);
        ReceitaEntity entity = objectMapper.convertValue(receita, ReceitaEntity.class);
        entity.setUsuarioEntity(usuarioEntityConvertido);
        ReceitaEntity receitaEntityAdicionada = receitaRepository.save(entity);
        return convertToDTO(receitaEntityAdicionada);
        }catch (RegraDeNegocioException e){
            throw new RuntimeException(e);
        }
    }

    public void remove(Integer idReceita) throws RegraDeNegocioException {

        //valida se a receita exite no banco de dados
        Optional<ReceitaEntity> receitaBuscada = receitaRepository.findById(idReceita);
        if (receitaBuscada.isEmpty()) {
            throw new RegraDeNegocioException("Receita não encontrada!");
        }

        //valida se a receita a ser atualizada é do usuário logado
        Integer userId = usuarioService.getIdLoggedUser();
        ReceitaEntity receitaEntity = receitaBuscada.get();
        if (!receitaEntity.getUsuarioEntity().getIdUsuario().equals(userId)) {
            throw new RegraDeNegocioException("ID de receita inválido.");
        }

        ReceitaEntity receitaEntity2 = returnReceitaEntityById(idReceita);
        receitaRepository.delete(receitaEntity2);
    }

    public ReceitaDTO update(Integer idReceita, ReceitaCreateDTO receita) throws RegraDeNegocioException {

        //valida se a receita exite no banco de dados
        Optional<ReceitaEntity> receitaBuscada = receitaRepository.findById(idReceita);
        if (receitaBuscada.isEmpty()) {
            throw new RegraDeNegocioException("Receita não encontrada!");
        }

        //valida se a receita a ser atualizada é do usuário logado
        Integer userId = usuarioService.getIdLoggedUser();
        ReceitaEntity receitaEntity = receitaBuscada.get();
        if (!receitaEntity.getUsuarioEntity().getIdUsuario().equals(userId)) {
            throw new RegraDeNegocioException("ID de receita inválido.");
        }

        ReceitaEntity receitaEntity2 = returnReceitaEntityById(idReceita);

        receitaEntity2.setValor(receita.getValor());
        receitaEntity2.setDescricao(receita.getDescricao());
        receitaEntity2.setBanco(receita.getBanco());
        receitaEntity2.setEmpresa(receita.getEmpresa());

        return convertToDTO(receitaRepository.save(receitaEntity2));

    }


    public Double valorTotal(){
        return findByUsuario(usuarioService.getIdLoggedUser()).stream()
                .mapToDouble(ReceitaDTO::getValor)
                .sum();
    }


    public Long totalRegistros(){
        return findByUsuario(usuarioService.getIdLoggedUser()).stream()
                .count();
    }


    public List<ReceitaDTO> findAll(Integer pagina, Integer quantidadeRegistros) {
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<ReceitaEntity> receitas = receitaRepository.findAll(pageable);
        List<ReceitaDTO> receitasDTO = this.convertToDTOList(receitas.getContent());
        return receitasDTO;
    }

    public List<ReceitaDTO> findByUsuario(Integer idUsuario) {
        try {
            UsuarioDTO usuarioById = usuarioService.findByUsuarioEntity(idUsuario);
            UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioById, UsuarioEntity.class);
            if (usuarioEntity == null) {
                throw new RegraDeNegocioException("Usuario não encontrado");
            }
            List<ReceitaEntity> receitaEntities = receitaRepository.findByUsuarioEntity(usuarioEntity);
            List<ReceitaDTO> receitasDTO = this.convertToDTOList(receitaEntities);
            return receitasDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public ReceitaDTO findById(Integer idReceita) throws RegraDeNegocioException {

        //valida se a receita exite no banco de dados
        Optional<ReceitaEntity> receitaBuscada = receitaRepository.findById(idReceita);
        if (receitaBuscada.isEmpty()) {
            throw new RegraDeNegocioException("Receita não encontrada!");
        }

        //valida se a receita a ser atualizada é do usuário logado
        Integer userId = usuarioService.getIdLoggedUser();
        ReceitaEntity receitaEntity = receitaBuscada.get();
        if (!receitaEntity.getUsuarioEntity().getIdUsuario().equals(userId)) {
            throw new RegraDeNegocioException("ID de receita inválido.");
        }

        return convertToDTO(returnReceitaEntityById(idReceita));
    }

    public ReceitaEntity returnReceitaEntityById(Integer id) {
        try {
            return receitaRepository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Receita não encontrada"));
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    private ReceitaDTO convertToDTO(ReceitaEntity receitaEntity) {
        ReceitaDTO receitaDTO = objectMapper.convertValue(receitaEntity, ReceitaDTO.class);

        return receitaDTO;
    }

    private List<ReceitaDTO> convertToDTOList(List<ReceitaEntity> listaReceitaEntities) {
        return listaReceitaEntities.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

}
