package sesame.projet_evaluation.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String nom;

    @NotBlank
    @Column(length = 2000)
    private String description;

    private String adminName;

    private String nbEtudiants;

    private String ngEnseignants;

    private String nbClasses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getNbEtudiants() {
        return nbEtudiants;
    }

    public void setNbEtudiants(String nbEtudiants) {
        this.nbEtudiants = nbEtudiants;
    }

    public String getNgEnseignants() {
        return ngEnseignants;
    }

    public void setNgEnseignants(String ngEnseignants) {
        this.ngEnseignants = ngEnseignants;
    }

    public String getNbClasses() {
        return nbClasses;
    }

    public void setNbClasses(String nbClasses) {
        this.nbClasses = nbClasses;
    }
}
