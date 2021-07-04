package cerveja.gerenciamento.testes.unitarios.service;

import cerveja.gerenciamento.testes.unitarios.builder.CervejaDTOBuilder;
import cerveja.gerenciamento.testes.unitarios.builder.FornecedoraDTOBuilder;
import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.dto.FornecedoraDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Cerveja;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.exception.FornecedoraAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.mapper.CervejaMapper;
import cerveja.gerenciamento.testes.unitarios.mapper.FornecedoraMapper;
import cerveja.gerenciamento.testes.unitarios.repository.CervejaRepository;
import cerveja.gerenciamento.testes.unitarios.repository.FornecedoraRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class FornecedoraServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private FornecedoraRepository fornecedoraRepository;

    private FornecedoraMapper fornecedoraMapper = FornecedoraMapper.INSTANCE;

    @InjectMocks
    private FornecedoraService fornecedoraService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws FornecedoraAlreadyRegisteredException {
        // given
        FornecedoraDTO expectedFornecedoraDTO = FornecedoraDTOBuilder.builder().build().toFornecedoraDTO();
        Cerveja expectedSavedCerveja = fornecedoraMapper.toModel(expectedFornecedoraDTO);

        // when
        when(fornecedoraRepository.findByName(expectedFornecedoraDTO.getName())).thenReturn(Optional.empty());
        when(fornecedoraRepository.save(expectedSavedFornecedora)).thenReturn(expectedSavedFornecedora);

        //then
        FornecedoraDTO createdFornecedoraDTO = fornecedoraService.createFornecedora(expectedFornecedoraDTO);

        assertThat(createdFornecedoraDTO.getId(), is(equalTo(expectedFornecedoraDTO.getId())));
        assertThat(createdFornecedoraDTO.getName(), is(equalTo(expectedFornecedoraDTO.getName())));
        assertThat(createdFornecedoraDTO.getQuantity(), is(equalTo(expectedFornecedoraDTO.getQuantity())));
    }


}
