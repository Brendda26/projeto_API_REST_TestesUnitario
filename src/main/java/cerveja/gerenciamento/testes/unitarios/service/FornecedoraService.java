package cerveja.gerenciamento.testes.unitarios.service;

import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.dto.FornecedoraDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Cerveja;
import cerveja.gerenciamento.testes.unitarios.entity.Fornecedora;
import cerveja.gerenciamento.testes.unitarios.exception.FornecedoraAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.mapper.FornecedoraMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FornecedoraService {

    private final FornecedoraRepository FornecedoraRepository;
    private final FornecedoraMapper fornecedoraMapper = FornecedoraMapper.INSTANCE;

    public CervejaDTO createCerveja(FornecedoraDTO beerDTO) throws FornecedoraAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(fornecedoraDTO.getName());
        Fornecedora fornecedora = fornecedoraMapper.toModel(fornecedoraDTO);
        Cerveja savedCerveja = fornecedoraRepository.save(fornecedora);
        return fornecedoraMapper.toDTO(savedFornecedora);
    }
}
