package cerveja.gerenciamento.testes.unitarios.controller;

import cerveja.gerenciamento.testes.unitarios.builder.CervejaDTOBuilder;
import cerveja.gerenciamento.testes.unitarios.builder.FornecedoraDTOBuilder;
import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.dto.FornecedoraDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Fornecedora;
import cerveja.gerenciamento.testes.unitarios.service.CervejaService;
import cerveja.gerenciamento.testes.unitarios.service.FornecedoraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static cerveja.gerenciamento.testes.unitarios.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FornecedoraControllerTest {

    private static final String FORNECEDORA_API_URL_PATH = "/api/v1/fornecedora";
    private static final long VALID_BEER_ID = 1L;
    private static final long INVALID_BEER_ID = 2l;
    private static final String FORNECEDORA__API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String FORNECEDORA__API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private FornecedoraService fornecedoraService;

    @InjectMocks
    private FornecedoraController fornecedoraController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fornecedoraController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
        // given
        FornecedoraDTO fornecedoraDTO = FornecedoraDTOBuilder.builder().build().toFornecedoraDTO();

        // when
        when(fornecedoraService.createFornecedora(fornecedoraDTO).thenReturn(fornecedoraDTO);

        // then
        mockMvc.perform(post(FORNECEDORA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fornecedoraDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(fornecedoraDTO.getName())))
                .andExpect(jsonPath("$.brand", is(fornecedoraDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(fornecedoraDTO.getType().toString())));
    }




}
