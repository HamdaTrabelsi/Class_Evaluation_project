package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Evaluation;

import java.util.List;

@Repository
public interface EvaluationRepository  extends JpaRepository<Evaluation, Long> {

    @Query("select e from Evaluation e where e.classe.id =:classeId")
    public List<Evaluation> getEvaluationByClasse(@Param("classeId") Long classeId);
}
