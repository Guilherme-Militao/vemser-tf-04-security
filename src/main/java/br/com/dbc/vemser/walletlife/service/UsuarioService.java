package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.*;
import br.com.dbc.vemser.walletlife.entity.CargoEntity;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import br.com.dbc.vemser.walletlife.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    // criação de um objeto
    public UsuarioDTO create(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        CargoEntity cargoEntity = cargoService.getByid(2);

        UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuario, UsuarioEntity.class);

        usuarioEntityConvertido.addCargo(cargoEntity);
        usuarioEntityConvertido.setSenha(passwordEncoder.encode(usuarioEntityConvertido.getSenha()));
        usuarioEntityConvertido.setLogin(usuario.getEmail());

        UsuarioEntity usuarioEntityCriado = usuarioRepository.save(usuarioEntityConvertido);


        return convertToDTO(usuarioEntityCriado);
    }


    public void remove(Integer idUsuario) {


        usuarioRepository.deleteById(getIdLoggedUser());
    }

    // atualização de um objeto
    public UsuarioDTO update(Integer id, UsuarioCreateDTO usuario)  throws Exception{

        UsuarioEntity usuarioEntity = usuarioRepository.findById(getIdLoggedUser())
                .orElseThrow(()-> new RegraDeNegocioException("Usuario não encontrado"));

        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setLogin(usuario.getEmail());
        usuarioEntity.setNome(usuario.getNome());
        usuarioEntity.setDataNascimento(usuario.getDataNascimento());

        return convertToDTO(usuarioRepository.save(usuarioEntity));

    }


    public UsuarioDTO findByUsuarioEntity(Integer id) {
        Integer idUser = id;
        try {
            Optional<UsuarioEntity> usuarioExisteOp = usuarioRepository.findById(getIdLoggedUser());
            if (usuarioExisteOp.isEmpty()) {
                throw new RegraDeNegocioException("Usuário não encontrado");
            }
            UsuarioEntity usuarioEntityExiste = usuarioExisteOp.get();
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntityExiste, UsuarioDTO.class);

            return usuarioDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UsuarioDTO> findAll() {
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .map(
                        usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class)
                ).collect(Collectors.toList());
        return usuarios;
    }

    public Set<UsuarioComDespesaDTO> findAllUsuariosDespesa(){
        return usuarioRepository.findAllUsuariosDespesa(getIdLoggedUser());
    }

    public List<UsuarioComReceitaDTO> findAllUsuarioReceita(Double valor, Integer pagina, Integer quantidadeRegistros){
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<UsuarioComReceitaDTO> receitas = usuarioRepository.findallUsuarioReceita(valor, pageable, getIdLoggedUser());
        List<UsuarioComReceitaDTO> usuarioComReceitaDTOS = receitas.getContent();
        return usuarioComReceitaDTOS;
    }

    public List<UsuarioComInvestimentoDTO> findUsuariosByInvestimentoCorretora(String corretora, Integer pagina, Integer quantidadeRegistros){
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<UsuarioComInvestimentoDTO> investimento = usuarioRepository.findUsuariosByInvestimentoCorretora(corretora, pageable, getIdLoggedUser());
        List<UsuarioComInvestimentoDTO> usuarioComInvestimentoDTOS = investimento.getContent();
        return usuarioComInvestimentoDTOS;
    }

    public List<UsuarioDadosDTO> findUsuarioDados(Integer idUsuario, Integer pagina, Integer quantidadeRegistros) throws RegraDeNegocioException {

        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<UsuarioEntity> dados = usuarioRepository.findAllComOptional(getIdLoggedUser(), pageable);
        List<UsuarioEntity> usuarioEntityDadosDTOS = dados.getContent();

        return usuarioEntityDadosDTOS.stream().map(
                usuario -> new UsuarioDadosDTO(usuario)
        ).collect(Collectors.toList());
    }

    private UsuarioDTO convertToDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        return usuarioDTO;
    }

    private List<UsuarioDTO> convertToDTOList(List<UsuarioEntity> listaUsuarioEntities) {
        return listaUsuarioEntities.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }



    public Integer getIdLoggedUser() {
        Integer findUserId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return findUserId;
    }

    public UsuarioLoggedDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity= findById(getIdLoggedUser());
        UsuarioLoggedDTO usuarioLoggedDTO = objectMapper.convertValue(usuarioEntity,UsuarioLoggedDTO.class);
        usuarioLoggedDTO.setCargos(usuarioEntity.getCargos());
        return usuarioLoggedDTO;
    }

    public UsuarioDTO updateSenha(Integer id, @Valid UsuarioSenhaDTO usuarioSenhaDTO) {
        UsuarioEntity usuarioExisteOp = usuarioRepository.getById(getIdLoggedUser());

        UsuarioDTO usuarioDTOSenha = objectMapper.convertValue(usuarioSenhaDTO, UsuarioDTO.class);

        usuarioExisteOp.setSenha(passwordEncoder.encode(usuarioDTOSenha.getSenha()));

        BeanUtils.copyProperties(usuarioExisteOp, usuarioDTOSenha, "idUsuario", "receitaEntities", "despesaEntities", "investimentoEntities", "login", "nome", "dataNascimento", "cpf", "email","cargos");

        UsuarioEntity usuarioEntityAtualizado = usuarioRepository.save(usuarioExisteOp);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntityAtualizado, UsuarioDTO.class);


        return usuarioDTO;
    }


    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuário não encontrado!"));
    }
}
