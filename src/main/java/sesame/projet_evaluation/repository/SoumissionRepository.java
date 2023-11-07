package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Soumission;

import java.util.List;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long> {

    @Query("select s from Soumission s where s.evaluation.id = :evaluationId and s.utilisateur.id = :utilisateurId")
    public List<Soumission> getSoumissionByEvaluationAndUtilisateur(@Param("evaluationId") Long evaluationId, @Param("utilisateurId") Long utilisateurId);

    @Query("select s from Soumission s where s.evaluation.id = :evaluationId")
    public List<Soumission> getSoumissionByEvaluation(@Param("evaluationId") Long evaluationId);
}
