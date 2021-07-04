package cerveja.gerenciamento.testes.unitarios.mapper;

import cerveja.gerenciamento.testes.unitarios.dto.FornecedoraDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Fornecedora;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public class FornecedoraMapper {
    FornecedoraMapper INSTANCE = Mappers.getMapper(CervejaMapper.class);

    Fornecedora toModel(FornecedoraDTO fornecedoraDTO);

    FornecedoraDTO toDTO(Fornecedora fornecedora);
}
