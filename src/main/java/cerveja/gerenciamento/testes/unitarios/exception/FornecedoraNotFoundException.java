package cerveja.gerenciamento.testes.unitarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FornecedoraNotFoundException {
    public FornecedoraNotFoundException(String fornecedoraNomeFornecedora) {
        super(String.format("Fornecedora with name %s not found in the system.", FornecedoraNomeFornecedora));
    }

    public FornecedoraNotFoundException(Long id) {
        super(String.format("Fornecedora with id %s not found in the system.", id));
    }
}
