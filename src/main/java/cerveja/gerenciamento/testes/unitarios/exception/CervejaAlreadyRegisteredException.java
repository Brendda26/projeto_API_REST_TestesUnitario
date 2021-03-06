package cerveja.gerenciamento.testes.unitarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CervejaAlreadyRegisteredException {
    public CervejaAlreadyRegisteredException(String beerName) {
        super(String.format("Beer with name %s already registered in the system.", cervejaName));
    }
}
