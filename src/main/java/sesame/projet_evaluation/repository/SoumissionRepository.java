package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.dto.EtudiantDTO;
import sesame.projet_evaluation.entities.Soumission;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.utils.ERole;

import java.util.List;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long> {

    @Query("select s from Soumission s where s.evaluation.id = :evaluationId and s.utilisateur.id = :utilisateurId")
    public List<Soumission> getSoumissionByEvaluationAndUtilisateur(@Param("evaluationId") Long evaluationId, @Param("utilisateurId") Long utilisateurId);

    @Query("select s from Soumission s where s.evaluation.id = :evaluationId")
    public List<Soumission> getSoumissionByEvaluation(@Param("evaluationId") Long evaluationId);

    @Query("select new sesame.projet_evaluation.dto.EtudiantDTO(u.id,u.email ,s.dateCreation ,u.classe, s.id) from Utilisateur u join Soumission s on u.id=s.utilisateur.id " +
            "join Evaluation e on s.evaluation.id = e.id where e.id = :evaluationId")
    public  List<EtudiantDTO> getEtudiantsByEvaluation(@Param("evaluationId") Long evaluationId);

    @Query("SELECT COUNT(s) from Soumission s join Utilisateur u on s.utilisateur.id = u.id JOIN u.roles r where s.evaluation.id = :evaluationId and r.name = :roleName")
    public Integer countSoumissionForEvaluation(@Param("evaluationId") Long evaluationId, @Param("roleName") ERole role);
}
