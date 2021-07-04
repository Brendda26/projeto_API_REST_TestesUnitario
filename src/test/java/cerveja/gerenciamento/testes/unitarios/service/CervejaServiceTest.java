package cerveja.gerenciamento.testes.unitarios.service;

import cerveja.gerenciamento.testes.unitarios.builder.CervejaDTOBuilder;
import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Cerveja;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaAlreadyRegisteredException;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaNotFoundException;
import cerveja.gerenciamento.testes.unitarios.exception.CervejaStockExceededException;
import cerveja.gerenciamento.testes.unitarios.mapper.CervejaMapper;
import cerveja.gerenciamento.testes.unitarios.repository.CervejaRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CervejaServiceTest {
    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private CervejaRepository cervejaRepository;

    private CervejaMapper cervejaMapper = CervejaMapper.INSTANCE;

    @InjectMocks
    private CervejaService cervejaService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws CervejaAlreadyRegisteredException {
        // given
        CervejaDTO expectedCervejaDTO = CervejaDTOBuilder.builder().build().toCervejDTO();
        Cerveja expectedSavedCerveja = cervejaMapper.toModel(expectedCervejaDTO);

        // when
        when(cervejaRepository.findByName(expectedCervejaDTO.getName())).thenReturn(Optional.empty());
        when(cervejaRepository.save(expectedSavedCerveja)).thenReturn(expectedSavedCerveja);

        //then
        CervejaDTO createdCervejaDTO = cervejaService.createCerveja(expectedCervejaDTO);

        assertThat(createCervejaDTO.getId(), is(equalTo(expectedCervejaDTO.getId())));
        assertThat(createCervejaDTO.getName(), is(equalTo(expectedCervejaDTO.getName())));
        assertThat(createCervejaDTO.getQuantity(), is(equalTo(expectedCervejaDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
        // given
        CervejaDTO expectedCervejaDTO = CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja duplicatedCerveja = CervejaMapper.toModel(expectedCervejaDTO);

        // when
        when(cervejaRepository.findByName(expectedCervejaDTO.getName())).thenReturn(Optional.of(duplicatedCerveja));

        // then
        assertThrows(CervejaAlreadyRegisteredException.class, () -> cervejaService.createCerveja(expectedCervejaDTO));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws CervejaNotFoundException {
        // given
        CervejaDTO expectedFoundCervejaDTO = CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja expectedFoundCerveja = cervejaMapper.toModel(expectedFoundCervejaDTO);

        // when
        when(cervejaRepository.findByName(expectedFoundCerveja.getName())).thenReturn(Optional.of(expectedFoundCerveja));

        // then
        CervejaDTO foundCervejaDTO = cervejaService.findByName(expectedFoundCervejaDTO.getName());

        assertThat(foundCervejaDTO, is(equalTo(expectedFoundCervejaDTO)));
    }

    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
        // given
        CervejaDTO expectedFoundBeerDTO = CervejaDTOBuilder.builder().build().toCervejaDTO();

        // when
        when(cervejaRepository.findByName(expectedFoundCervejaDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(CervejaNotFoundException.class, () -> cervejaService.findByName(expectedFoundCervejaDTO.getName()));
    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        // given
        CervejaDTO expectedFoundCervejaDTO = CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja expectedFoundCerveja = cervejaMapper.toModel(expectedFoundCervejaDTO);

        //when
        when(cervejaRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundCerveja));

        //then
        List<CervejaDTO> foundListCervejaDTO = cervejaService.listAll();

        assertThat(foundListCervejaDTO , is(not(empty())));
        assertThat(foundListCervejaDTO .get(0), is(equalTo(expectedFoundCervejaDTO)));
    }

    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {
        //when
        when(cervejaRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<CervejaDTO> foundListCervejaDTO  = cervejaService.listAll();

        assertThat(foundListCervejaDTO , is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws CervejaNotFoundException{
        // given
        CervejaDTO expectedDeletedCervejaDTO = CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja expectedDeletedCerveja =  CervejaMapper.toModel(expectedDeletedCervejaDTO);

        // when
        when(cervejaRepository.findById(expectedDeletedCervejaDTO.getId())).thenReturn(Optional.of(expectedDeletedCerveja));
        doNothing().when(cervejaRepository).deleteById(expectedDeletedCervejaDTO.getId());

        // then
        cervejaService.deleteById(expectedDeletedCervejaDTO.getId());

        verify(cervejaRepository, times(1)).findById(expectedDeletedCervejaDTO.getId());
        verify(cervejaRepository, times(1)).deleteById(expectedDeletedCervejaDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() throws CervejaNotFoundException, CervejaStockExceededException {
        //given
        CervejaDTO expectedCervejaDTO =  CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja expectedCerveja = cervejaMapper.toModel(expectedCervejaDTO);

        //when
        when(cervejaRepository.findById(expectedCervejaDTO.getId())).thenReturn(Optional.of(expectedCerveja));
        when(cervejaRepository.save(expectedCerveja).thenReturn(expectedCerveja);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedCervejaDTO.getQuantity() + quantityToIncrement;

        // then
        CervejaDTO incrementedBeerDTO = cervejaService.increment(expectedCervejaDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedBeerDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedCervejaDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        CervejaDTO expectedCervejaDTO =  CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja expectedCerveja = cervejaMapper.toModel(expectedCervejaDTO);

        when(cervejaRepository.findById(expectedCervejaDTO.getId())).thenReturn(Optional.of(expectedCerveja));

        int quantityToIncrement = 80;
        assertThrows(CervejaStockExceededException.class, () -> cervejaService.increment(expectedCervejaDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        CervejaDTO expectedCervejaDTO =  CervejaDTOBuilder.builder().build().toCervejaDTO();
        Cerveja expectedCerveja = cervejaMapper.toModel(expectedCervejaDTO);


        when(cervejaRepository.findById(expectedCervejaDTO.getId())).thenReturn(Optional.of(expectedCerveja));

        int quantityToIncrement = 45;
        assertThrows(CervejaStockExceededException.class, () -> cervejaService.increment(expectedCervejaDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(cervejaRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        assertThrows(CervejaNotFoundException.class, () -> cervejaService.increment(INVALID_BEER_ID, quantityToIncrement));
    }
//
//    @Test
//    void whenDecrementIsCalledThenDecrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
//        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
//
//        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
//        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);
//
//        int quantityToDecrement = 5;
//        int expectedQuantityAfterDecrement = expectedBeerDTO.getQuantity() - quantityToDecrement;
//        BeerDTO incrementedBeerDTO = beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement);
//
//        assertThat(expectedQuantityAfterDecrement, equalTo(incrementedBeerDTO.getQuantity()));
//        assertThat(expectedQuantityAfterDecrement, greaterThan(0));
//    }
//
//    @Test
//    void whenDecrementIsCalledToEmptyStockThenEmptyBeerStock() throws BeerNotFoundException, BeerStockExceededException {
//        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
//
//        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
//        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);
//
//        int quantityToDecrement = 10;
//        int expectedQuantityAfterDecrement = expectedBeerDTO.getQuantity() - quantityToDecrement;
//        BeerDTO incrementedBeerDTO = beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement);
//
//        assertThat(expectedQuantityAfterDecrement, equalTo(0));
//        assertThat(expectedQuantityAfterDecrement, equalTo(incrementedBeerDTO.getQuantity()));
//    }
//
//    @Test
//    void whenDecrementIsLowerThanZeroThenThrowException() {
//        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
//
//        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
//
//        int quantityToDecrement = 80;
//        assertThrows(BeerStockExceededException.class, () -> beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement));
//    }
//
//    @Test
//    void whenDecrementIsCalledWithInvalidIdThenThrowException() {
//        int quantityToDecrement = 10;
//
//        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());
//
//        assertThrows(BeerNotFoundException.class, () -> beerService.decrement(INVALID_BEER_ID, quantityToDecrement));
//    }
}
