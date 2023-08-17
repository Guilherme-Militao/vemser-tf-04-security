package br.com.dbc.vemser.walletlife.doc;

import br.com.dbc.vemser.walletlife.dto.DespesaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.DespesaDTO;
import br.com.dbc.vemser.walletlife.exceptions.EntidadeNaoEncontradaException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface DespesaControllerDoc {

    @Operation(summary = "Lista todas as despesas", description = "Lista todas as despesas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de despesas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping //GET localhost:8080/despesa
    public ResponseEntity<List<DespesaDTO>> listarTodos();

    @Operation(summary = "Buscar despesa no banco", description = "Busca no banco a despesa a partir de um ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma despesa"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idDespesa}") //GET localhost:8080/despesa/1
    public ResponseEntity<DespesaDTO> buscarDespesas(@PathVariable("idDespesa") Integer id) throws RegraDeNegocioException, EntidadeNaoEncontradaException;

    @Operation(summary = "Listar despesas de um usuário", description = "Busca no banco as despesas de um usuário utilizando o ID do usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna as despesas de um usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario/{idUsuario}")//GET localhost:8080/despesa/usuario/1
    public ResponseEntity<List<DespesaDTO>> listarDespesasPorUsuario(@PathVariable("idUsuario") @Positive Integer id) throws RegraDeNegocioException;


    @Operation(summary = "Insere uma nova despesa", description = "Insere uma nova despesa no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a despesa criada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<DespesaDTO> adicionarDespesa(@PathVariable("idUsuario") Integer idUsuario,@Valid @RequestBody DespesaCreateDTO despesa) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza uma despesa por ID", description = "Busca no banco a despesa a partir de um ID e a atualiza")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a despesa atualizada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idDespesa}")
    public ResponseEntity<DespesaDTO> editarDepesa(@PathVariable("idDespesa") Integer id,
                                                   @Valid @RequestBody DespesaCreateDTO despesaAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Deleta uma despesa por ID", description = "Busca no banco a despesa a partir de um ID e a deleta")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta uma despesa do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idDespesa}")
    public ResponseEntity<Void> removerDespesa(@PathVariable("idDespesa") Integer id) throws RegraDeNegocioException;

}
