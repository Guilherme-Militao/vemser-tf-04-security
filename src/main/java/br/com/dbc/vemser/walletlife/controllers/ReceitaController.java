package br.com.dbc.vemser.walletlife.controllers;

import br.com.dbc.vemser.walletlife.doc.ReceitaControllerDoc;
import br.com.dbc.vemser.walletlife.dto.ReceitaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.ReceitaDTO;
import br.com.dbc.vemser.walletlife.service.ReceitaService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@Data
@RequestMapping("/receita")
public class ReceitaController implements ReceitaControllerDoc {
    private final ReceitaService receitaService;

    @GetMapping
    public ResponseEntity<List<ReceitaDTO>> findAll(Integer pagina, Integer quantidadeRegistros) {
        return new ResponseEntity<>(receitaService.findAll(pagina, quantidadeRegistros), HttpStatus.OK);
    }

    @GetMapping("/{idReceita}")
    public ResponseEntity<ReceitaDTO> findById(@PathVariable("idReceita") @Positive Integer id){
        return new ResponseEntity<>(receitaService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReceitaDTO>> findByUsuario(@PathVariable("idUsuario") Integer id){
        return new ResponseEntity<>(receitaService.findByUsuario(id), HttpStatus.OK);
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<ReceitaDTO> create(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody ReceitaCreateDTO receita){
        return new ResponseEntity<>(receitaService.create(receita, idUsuario), HttpStatus.OK);
    }

    @PutMapping("/{idReceita}")
    public ResponseEntity<ReceitaDTO> update(@PathVariable("idReceita") Integer id,
                                             @Valid @RequestBody ReceitaDTO receitaAtualizar){
        return new ResponseEntity<>(receitaService.update(id, receitaAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{idReceita}")
    public ResponseEntity<Void> remove(@PathVariable("idReceita") Integer id) {
        receitaService.remove(id);
        return ResponseEntity.ok().build();
    }

}
