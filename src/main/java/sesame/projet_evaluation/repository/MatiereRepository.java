package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Matiere;
import sesame.projet_evaluation.entities.Utilisateur;

import java.util.List;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    boolean existsByClasseAndEnseignantAndSemestreAndNomIgnoreCase(Classe classe, Utilisateur enseignant, String semestre, String nom);

    @Query("Select m from Matiere m where m.classe.id =:classeId")
    List<Matiere> findMatieresByClass(@Param("classeId") Long classeId);
}
