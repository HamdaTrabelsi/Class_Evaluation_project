package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Classe;

@Repository
public interface ClassRepository extends JpaRepository<Classe, Long> {

    boolean existsClasseByAnneeUniversitaireAndNom(String anneeUniversitaire, String nom);
}
