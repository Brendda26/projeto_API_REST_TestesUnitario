package cerveja.gerenciamento.testes.unitarios.builder;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
public class FornecedoraDTOBuilder {

    @Builder.Default
    private Long id = 1l;

    @Builder.Default
    private String nomeFornecedora = "Distribuidora_Discol";

    @Builder.Default
    private String nomeResposnsavel = "Pedro Dourado";

    @Builder.Default
    private String endereco = "Rua da Avenida, nº 34"

    @Builder.Default
    private String cnpj = "000.897.677-40";
}
