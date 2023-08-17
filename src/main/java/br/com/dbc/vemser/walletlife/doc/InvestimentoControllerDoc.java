package br.com.dbc.vemser.walletlife.doc;

import br.com.dbc.vemser.walletlife.dto.InvestimentoCreateDTO;
import br.com.dbc.vemser.walletlife.dto.InvestimentoDTO;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

public interface InvestimentoControllerDoc {

    @Operation(summary = "Buscar todos os investimentos", description = "Busca no banco todos investimentos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de investimentos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<InvestimentoDTO>> findAll();

    @Operation(summary = "Buscar Investimento por ID", description = "Busca no banco o investimento a partir de um ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um investimento"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idInvestimento}")
    public ResponseEntity<InvestimentoDTO> findById(@PathVariable("idInvestimento") @Positive Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar Investimentos por ID de Usuário", description = "Lista todos os investimentos de um usuário a partir de um ID de usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de investimentos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<InvestimentoDTO>> findByUsuario(@PathVariable("idUsuario") @Positive Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Adicionar Investimento", description = "Adiciona um novo investimento no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o investimento criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<InvestimentoDTO> create(@RequestBody @Valid InvestimentoCreateDTO investimento, @PathVariable("idUsuario") @Positive Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar Investimento por ID", description = "Busca no banco o investimento a partir de um ID e o atualiza")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o investimento atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idInvestimento}")
    public ResponseEntity<InvestimentoDTO> update(@PathVariable("idInvestimento") @Valid Integer id,
                                                  @RequestBody InvestimentoCreateDTO investimentoAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Deletar Investimento por ID", description = "Busca no banco o investimento a partir de um ID e o deleta")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um corpo vazio"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idInvestimento}")
    public ResponseEntity<Void> remove(@PathVariable("idInvestimento") @Positive Integer id) throws Exception;
}
