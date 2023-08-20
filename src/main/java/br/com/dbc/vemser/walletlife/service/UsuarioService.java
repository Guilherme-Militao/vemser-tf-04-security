package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.*;
import br.com.dbc.vemser.walletlife.entity.CargoEntity;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.entity.UsuarioEntity;
import br.com.dbc.vemser.walletlife.repository.CargoRepository;
import br.com.dbc.vemser.walletlife.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    // criação de um objeto
    public UsuarioDTO create(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        CargoEntity cargoEntity = cargoService.getByid(usuario.getTipoCargo());

        try {
            UsuarioEntity usuarioEntityConvertido = objectMapper.convertValue(usuario, UsuarioEntity.class);
            usuarioEntityConvertido.addCargo(cargoEntity);
            UsuarioEntity usuarioEntityCriado = usuarioRepository.save(usuarioEntityConvertido);
            cargoEntity.addUser(usuarioEntityConvertido);
            UsuarioDTO novoUsuario = this.convertToDTO(usuarioEntityCriado);
            return novoUsuario;

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
        return null;
    }

//            Map<String, String> dados = new HashMap<>();
//            dados.put("nome", novoUsuario.getNomeCompleto());
//            String paragrafo = "Estamos felizes em tê-lo como usuário do Wallet Life! :) <br>" +
//                    "           Seu cadastro foi realizado com sucesso, e agora você pode organizar todas suas finanças!.<br>" +
//                    "           Aproveite para acessar nossa plataforma e descobrir mais sobre o projeto!<br>";
//            dados.put("paragrafo", paragrafo);
//            dados.put("email", novoUsuario.getEmail());
//            emailService.sendTemplateEmail(dados);

    public void remove(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // atualização de um objeto
    public UsuarioDTO update(Integer id, UsuarioCreateDTO usuario) {
        try {
            Optional<UsuarioEntity> usuarioExisteOp = usuarioRepository.findById(id);
            if (usuarioExisteOp.isEmpty()) {
                throw new RegraDeNegocioException("Usuário não encontrado");
            }
            UsuarioEntity usuarioEntityDados = objectMapper.convertValue(usuario, UsuarioEntity.class);
            UsuarioEntity usuarioEntityExiste = usuarioExisteOp.get();

            BeanUtils.copyProperties(usuarioEntityDados, usuarioEntityExiste, "idUsuario", "receitas", "despesas", "investimentos" );

            UsuarioEntity usuarioEntityAtualizado = usuarioRepository.save(usuarioEntityExiste);
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntityAtualizado, UsuarioDTO.class);

            return usuarioDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    //            Map<String, String> dados = new HashMap<>();
//            dados.put("nome", usuarioDTO.getNomeCompleto());
//            String paragrafo = "Parece que você atualizou seus dados!<br>" +
//                               "Deu tudo certo na operação.<br>" +
//                               "Pode ficar tranquile! :)";
//            dados.put("paragrafo", paragrafo);
//            dados.put("email", usuarioDTO.getEmail());
//            emailService.sendTemplateEmail(dados);

    public UsuarioDTO findByUsuarioEntity(Integer id) {
        try {
            Optional<UsuarioEntity> usuarioExisteOp = usuarioRepository.findById(id);
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
        return usuarioRepository.findAllUsuariosDespesa();
    }

    public List<UsuarioComReceitaDTO> findAllUsuarioReceita(Double valor, Integer pagina, Integer quantidadeRegistros){
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<UsuarioComReceitaDTO> receitas = usuarioRepository.findallUsuarioReceita(valor, pageable);
        List<UsuarioComReceitaDTO> usuarioComReceitaDTOS = receitas.getContent();
        return usuarioComReceitaDTOS;
    }

    public List<UsuarioComInvestimentoDTO> findUsuariosByInvestimentoCorretora(String corretora, Integer pagina, Integer quantidadeRegistros){
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<UsuarioComInvestimentoDTO> investimento = usuarioRepository.findUsuariosByInvestimentoCorretora(corretora, pageable);
        List<UsuarioComInvestimentoDTO> usuarioComInvestimentoDTOS = investimento.getContent();
        return usuarioComInvestimentoDTOS;
    }

    public List<UsuarioDadosDTO> findUsuarioDados(Integer idUsuario, Integer pagina, Integer quantidadeRegistros) throws RegraDeNegocioException {
        if (idUsuario != null){
            Optional<UsuarioEntity> usuarioOP = usuarioRepository.findById(idUsuario);
            if (usuarioOP.isEmpty()){
                throw new RegraDeNegocioException("Usuário não encontrado");
            }
        }
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros);
        Page<UsuarioEntity> dados = usuarioRepository.findAllComOptional(idUsuario, pageable);
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

}
