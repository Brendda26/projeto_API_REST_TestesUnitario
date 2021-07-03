package cerveja.gerenciamento.testes.unitarios.service;

import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Cerveja;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaNotFoundException;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaStockExceededException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CervejaService {

    private final CervejaRepository cervejaRepository;
    private final CervejaMapper cervejaMapper = CervejaMapper.INSTANCE;

    public CervejaDTO createCerveja(BeerDTO beerDTO) throws CervejaAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(cervejaDTO.getName());
        Cerveja cerveja = cervejaMapper.toModel(cervejaDTO);
        Cerveja savedCerveja = cervejaRepository.save(cerveja);
        return cervejaMapper.toDTO(savedCerveja);
    }

    public CervejaDTO findByName(String name) throws CervejaNotFoundException {
        Cerveja foundBeer = cervejaRepository.findByName(name)
                .orElseThrow(() -> new CervejaNotFoundException(name));
        return cervejaMapper.toDTO(foundCerveja);
    }

    public List<CervejaDTO> listAll() {
        return cervejaRepository.findAll()
                .stream()
                .map(cervejaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws CervejaNotFoundException {
        verifyIfExists(id);
        cervejaRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws CervejaAlreadyRegisteredException {
        Optional<Cerveja> optSavedBeer = cervejaRepository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new CervejaAlreadyRegisteredException(name);
        }
    }

    private Cerveja verifyIfExists(Long id) throws CervejaNotFoundException {
        return cervejaRepository.findById(id)
                .orElseThrow(() -> new CervejaNotFoundException(id));
    }

    public CervejaDTO increment(Long id, int quantityToIncrement) throws CervejaNotFoundException, CervejaStockExceededException {
        Cerveja cervejaToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + cervejaToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= cervejaToIncrementStock.getMax()) {
            cervejaToIncrementStock.setQuantity(cervejaToIncrementStock.getQuantity() + quantityToIncrement);
            Cerveja incrementedBeerStock = cervejaRepository.save(cervejaToIncrementStock);
            return cervejaMapper.toDTO(incrementedBeerStock);
        }
        throw new CervejaStockExceededException(id, quantityToIncrement);
    }

}
