package br.com.dbc.vemser.walletlife.controllers;

import br.com.dbc.vemser.walletlife.doc.InvestimentoControllerDoc;
import br.com.dbc.vemser.walletlife.dto.InvestimentoCreateDTO;
import br.com.dbc.vemser.walletlife.dto.InvestimentoDTO;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.service.InvestimentoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/investimentos")
public class InvestimentoController implements InvestimentoControllerDoc {

    private final InvestimentoService investimentoService;

    @GetMapping
    public ResponseEntity<List<InvestimentoDTO>> findAll() {
        List<InvestimentoDTO> investimentoDTO = investimentoService.findAll();
        return ResponseEntity.ok(investimentoDTO);
    }
    @GetMapping("/{idInvestimento}")
    public ResponseEntity<InvestimentoDTO> findById(@PathVariable("idInvestimento") @Positive Integer id) throws RegraDeNegocioException {
        InvestimentoDTO investimentoDTO = investimentoService.findById(id);
        return ResponseEntity.ok(investimentoDTO);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<InvestimentoDTO>> findByUsuario(@PathVariable("idUsuario") @Positive Integer id) throws RegraDeNegocioException {
        List<InvestimentoDTO> investimentos = investimentoService.findByUsuario(id);
        return ResponseEntity.ok(investimentos);
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<InvestimentoDTO> create(@RequestBody @Valid InvestimentoCreateDTO investimento, @PathVariable("idUsuario") @Positive Integer id) throws RegraDeNegocioException {
        InvestimentoDTO novoInvestimento = investimentoService.create(investimento, id);
        return ResponseEntity.status(HttpStatus.OK).body(novoInvestimento);
    }

    @PutMapping("/{idInvestimento}")
    public ResponseEntity<InvestimentoDTO> update(@PathVariable("idInvestimento") @Valid Integer id,
                                                  @RequestBody InvestimentoCreateDTO investimentoAtualizar) throws RegraDeNegocioException {
        InvestimentoDTO investimentoAtualizado = investimentoService.update(id, investimentoAtualizar);
        return ResponseEntity.ok(investimentoAtualizado);
    }

    @DeleteMapping("/{idInvestimento}")
    public ResponseEntity<Void> remove(@PathVariable("idInvestimento") @Positive Integer id) throws Exception {
        investimentoService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
