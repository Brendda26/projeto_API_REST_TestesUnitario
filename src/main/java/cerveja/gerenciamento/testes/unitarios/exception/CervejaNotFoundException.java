package cerveja.gerenciamento.testes.unitarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CervejaNotFoundException {
    public CervejaNotFoundException(String beerName) {
        super(String.format("Beer with name %s not found in the system.", CervejaName));
    }

    public CervejaNotFoundException(Long id) {
        super(String.format("Beer with id %s not found in the system.", id));
    }
}
