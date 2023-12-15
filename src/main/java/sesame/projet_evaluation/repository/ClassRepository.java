package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sesame.projet_evaluation.entities.Classe;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classe, Long> {

    boolean existsClasseByAnneeUniversitaireAndNom(String anneeUniversitaire, String nom);

    @Query("select c from Classe c where c.departement.id = :id")
    List<Classe> getClassesByDepartementId(@Param("id") Long departementId);
}
