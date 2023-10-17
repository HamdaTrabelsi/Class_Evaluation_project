package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Soumission;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long> {
}
