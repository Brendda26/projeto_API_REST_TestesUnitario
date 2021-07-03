package cerveja.gerenciamento.testes.unitarios.mapper;

import cerveja.gerenciamento.testes.unitarios.dto.CervejaDTO;
import cerveja.gerenciamento.testes.unitarios.entity.Cerveja;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public class CervejaMapper {
    CervejaMapper INSTANCE = Mappers.getMapper(CervejaMapper.class);

    Cerveja toModel(CervejaDTO cervejaDTO);

    CervejaDTO toDTO(Cerveja cerveja);
}
