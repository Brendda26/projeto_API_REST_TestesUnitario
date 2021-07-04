package cerveja.gerenciamento.testes.unitarios.controller;

import cerveja.gerenciamento.testes.unitarios.dto.FornecedoraDTO;
import cerveja.gerenciamento.testes.unitarios.exception.FornecedoraAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.service.FornecedoraService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public class FornecedoraController {

    private final FornecedoraService fornecedoraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedoraDTO createFornecedora(@RequestBody @Valid FornecedoraDTO fornecedoraDTO) throws FornecedoraAlreadyRegisteredException {
        return fornecedoraService.createFornecedora(fornecedoraDTO);
    }

}
