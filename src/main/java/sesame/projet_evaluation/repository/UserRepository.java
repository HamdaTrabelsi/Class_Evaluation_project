package sesame.projet_evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.utils.ERole;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByUsername(String username);

    @Query("select u from Utilisateur u where lower(u.email) = lower(:email)")
    Optional<Utilisateur> findByEmail(@Param("email") String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select count (u.id) from Utilisateur u where u.id <> :id and u.email = :email")
    Integer checkIfEmailExistsWhenUpdating(@Param("id") Long id, @Param("email") String email);

    @Query(value = "select count (u.id) from Utilisateur u where u.email = :email")
    Integer checkIfEmailExists(@Param("email") String email);

    @Query("SELECT u FROM Utilisateur u JOIN u.roles r WHERE r.name = :roleName")
    List<Utilisateur> findByRoleName(@Param("roleName") ERole roleName);

    @Query(nativeQuery = true, value ="SELECT * FROM utilisateur u  WHERE u.email = :email LIMIT 1")
    Optional<Utilisateur> findUserByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u.id) from Utilisateur u join u.roles r WHERE u.classe.id = :classeId and r.name = :roleName")
    Integer countClasseStudents(@Param("classeId") Long classeId,@Param("roleName") ERole roleName);

    @Query("SELECT c from Matiere m join Classe c on m.classe.id = c.id join Utilisateur u on m.enseignant.id = u.id where u.id = :enseignantId")
    List<Classe> getAnneeUniversitaireAndClasseByEnseignantId(@Param("enseignantId") Long enseignantId);


    @Query("SELECT Count(u) FROM Utilisateur u JOIN u.roles r WHERE r.name = :roleName")
    Integer countUsersByRole(@Param("roleName") ERole roleName);

    @Query("SELECT Count(u) FROM Utilisateur u ")
    Integer countUsers();

    @Query("SELECT Count(u) FROM Classe u ")
    Integer countClasses();

    @Query("SELECT Count(u) FROM Matiere u ")
    Integer countMatiere();

}
