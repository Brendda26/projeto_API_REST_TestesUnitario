package cerveja.gerenciamento.testes.unitarios.controller;
import cerveja.gerenciamento.testes.unitarios.dto.FornecedoraDTO;
import cerveja.gerenciamento.testes.unitarios.exception.FornecedoraAlreadyRegisteredException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages fornecedora ")
public class FornecedoraControllerDocs {

    @ApiOperation(value = "Fornecedora creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success beer creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    FornecedoraDTO createFornecedora(FornecedoraDTO fornecedoraDTO) throws FornecedoraAlreadyRegisteredException;

    @ApiOperation(value = "Returns fornecedora found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success fornecedora found in the system"),
            @ApiResponse(code = 404, message = "Fornecedora with given name not found.")
    })
    FornecedoraDTO findByName(@PathVariable String nomeFornecedora) throws FornecedoraNotFoundException;

    @ApiOperation(value = "Returns a list of all beers registered in the system")
    @ApiResponses(value = {/
            @ApiResponse(code = 200, message = "List of all beers registered in the system"),
    })
    List<FornecedoraDTO> listFornecedora();

    @ApiOperation(value = "Delete a beer found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success fornecedora deleted in the system"),
            @ApiResponse(code = 404, message = "Fornecedora with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws FornecedoraNotFoundException;


}
