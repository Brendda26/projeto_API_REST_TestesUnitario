package cerveja.gerenciamento.testes.unitarios.controller;

import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.dto.QuantidadeDTO;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaNotFoundException;
import cerveja.gerenciamento.testes.unitarios.service.CervejaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;

public class CervejaController {
    private final CervejaService cervejaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CervejaDTO createBeer(@RequestBody @Valid CervejaDTO cervejaDTO) throws CervejaAlreadyRegisteredException {
        return cervejaService.createCerveja(cervejaDTO);
    }

    @GetMapping("/{name}")
    public CervejaDTO findByName(@PathVariable String name) throws CervejaNotFoundException {
        return cervejaService.findByName(name);
    }

    @GetMapping
    public List<CervejaDTO> listCerveja() {
        return cervejaService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws CervejaNotFoundException {
        cervejaService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public CervejaDTO increment(@PathVariable Long id, @RequestBody @Valid QuantidadeDTO quantidadeDTO) throws CervejaNotFoundException, BeerStockExceededException {
        return cervejaService.increment(id, quantidadeDTO.getQuantity());
    }
}
