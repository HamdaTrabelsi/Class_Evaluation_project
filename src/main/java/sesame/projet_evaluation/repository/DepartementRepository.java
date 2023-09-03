package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sesame.projet_evaluation.entities.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
