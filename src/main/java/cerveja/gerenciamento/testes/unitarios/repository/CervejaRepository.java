package cerveja.gerenciamento.testes.unitarios.repository;

import cerveja.gerenciamento.testes.unitarios.entity.Cerveja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CervejaRepository extends JpaRepository<Cerveja, Long> {
    Optional<Cerveja> findByName(String name);
}
