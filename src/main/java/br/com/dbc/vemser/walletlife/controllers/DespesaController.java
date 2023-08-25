package br.com.dbc.vemser.walletlife.controllers;


import br.com.dbc.vemser.walletlife.dto.DespesaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.DespesaDTO;
import br.com.dbc.vemser.walletlife.exceptions.EntidadeNaoEncontradaException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.service.DespesaService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/despesa")
@Slf4j
@Data
public class DespesaController{

    private final DespesaService despesaService;

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> listarTodos(){
        return new ResponseEntity<>(despesaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idDespesa}")
    public ResponseEntity<DespesaDTO> buscarDespesas(@PathVariable("idDespesa") Integer id) throws RegraDeNegocioException, EntidadeNaoEncontradaException {
        return new ResponseEntity<>(despesaService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<DespesaDTO>> listarDespesasPorUsuario(@PathVariable("idUsuario") @Positive Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(despesaService.listarDespesaByIdUsuario(id), HttpStatus.OK);
    }

    @GetMapping("/valor-total")
    public ResponseEntity<Double> totalReceitas() throws RegraDeNegocioException {
        return new ResponseEntity<>(despesaService.valorTotal(), HttpStatus.OK);
    }

    @PostMapping("/criar-despesa")
    public ResponseEntity<DespesaDTO> adicionarDespesa(@Valid @RequestBody DespesaCreateDTO despesa) throws RegraDeNegocioException {
        return new ResponseEntity<>(despesaService.adicionarDespesa(despesa), HttpStatus.OK);
    }

    @PutMapping("/{idDespesa}")
    public ResponseEntity<DespesaDTO> editarDepesa(@PathVariable("idDespesa") Integer id,
                                                   @Valid @RequestBody DespesaCreateDTO despesaAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(despesaService.editarDespesa(id, despesaAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{idDespesa}")
    public ResponseEntity<Void> removerDespesa(@PathVariable("idDespesa") Integer id) throws RegraDeNegocioException{
        despesaService.removerDespesa(id);
        return ResponseEntity.ok().build();
    }
}
