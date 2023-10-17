package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Evaluation;

@Repository
public interface EvaluationRepository  extends JpaRepository<Evaluation, Long> {
}
