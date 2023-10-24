package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Matiere;
import sesame.projet_evaluation.entities.Utilisateur;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    boolean existsByClasseAndEnseignantAndSemestreAndNomIgnoreCase(Classe classe, Utilisateur enseignant, String semestre, String nom);
}
