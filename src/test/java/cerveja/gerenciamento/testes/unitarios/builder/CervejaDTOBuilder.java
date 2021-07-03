package cerveja.gerenciamento.testes.unitarios.builder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.enums.CervejaType;
import lombok.Builder;

@Builder
public class CervejaDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Brahma";

    @Builder.Default
    private String brand = "Ambev";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private CervejaType type = CervejaType.LAGER;

    public CervejaDTO toCervejaDTO() {
        return new CervejaDTO(id,
                name,
                brand,
                max,
                quantity,
                type);
    }
}
