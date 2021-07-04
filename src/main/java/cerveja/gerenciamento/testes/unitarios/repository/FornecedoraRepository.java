package cerveja.gerenciamento.testes.unitarios.repository;

import cerveja.gerenciamento.testes.unitarios.entity.Fornecedora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedoraRepository extends JpaRepository<Fornecedora, Long> {

    Optional<Fornecedora> findByNomeFornecedora(String nomeFornecedora);
}
