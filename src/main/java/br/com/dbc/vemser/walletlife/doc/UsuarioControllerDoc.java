package br.com.dbc.vemser.walletlife.doc;

import br.com.dbc.vemser.walletlife.dto.*;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

public interface UsuarioControllerDoc {

    @Operation(summary = "Lista todos os Usuários", description = "Lista todas os usuários do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll();

    @Operation(summary = "Listar Usuários por ID", description = "Busca no banco o usuário a partir de um ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("idUsuario") @Positive Integer idUsuario);

    @Operation(summary = "Listar Usuários e despesa", description = "Busca no banco o usuário e despesas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário com despesa"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-despesa")
    public ResponseEntity<Set<UsuarioComDespesaDTO>> findAllUsuariosDespesa();

    @Operation(summary = "Listar Usuários e suas receitas, todas, filtradas por um valor, e paginadas", description = "Busca no banco o usuário suas receitas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário com sua receita"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-receita")
    public ResponseEntity<List<UsuarioComReceitaDTO>> findallUsuarioReceita(
            Integer pagina, Integer quantidadeRegistros,
            @RequestParam(value = "valor", required = false) Double valor);


    @Operation(summary = "Listar Usuários e investimentos de maneira paginada, podendo passar nome de corretora como modo de busca",
            description = "Busca no banco o usuário e investimento podendo ter uma corretora como parâmetro")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário com investimento"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-investimento")
    public ResponseEntity<List<UsuarioComInvestimentoDTO>> findUsuariosByInvestimentoCorretora(
            Integer pagina, Integer quantidadeRegistros,
            @RequestParam(value = "corretora", required = false) String corretora);

    @Operation(summary = "Listar Usuários com todos os seus dados, podendo buscar o usuário por Id, paginando o resultado",
            description = "Busca no banco o usuário e todos seus dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário completo"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-dados")
    public ResponseEntity<List<UsuarioDadosDTO>> findUsuarioDados(
            @RequestParam(value = "idUsuario", required = false) Integer idUsuario, Integer pagina,
            Integer quantidadeRegistros) throws RegraDeNegocioException;




    @Operation(summary = "Insere um novo usuário", description = "Insere um novo usuário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o usuário criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuario);


    @Operation(summary = "Atualiza um Usuário por ID", description = "Busca no banco o usuário a partir de um ID e o atualiza")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable @Positive Integer idUsuario,
                                                   @RequestBody @Valid UsuarioCreateDTO usuario);

    @Operation(summary = "Deleta um Usuário por ID", description = "Busca no banco o usuário a partir de um ID e o deleta")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um corpo vazio"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> remove(@PathVariable Integer idUsuario);
}
