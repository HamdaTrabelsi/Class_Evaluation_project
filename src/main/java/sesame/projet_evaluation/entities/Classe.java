package sesame.projet_evaluation.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String nom;

    private String anneeUniversitaire;

    @ManyToOne
    private Departement departement;

    private Date dateCreation;

    private String nbEtudiants;

    private String nbEnseignants;

    public Classe() {
    }

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

    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNbEtudiants() {
        return nbEtudiants;
    }

    public void setNbEtudiants(String nbEtudiants) {
        this.nbEtudiants = nbEtudiants;
    }

    public String getNbEnseignants() {
        return nbEnseignants;
    }

    public void setNbEnseignants(String nbEnseignants) {
        this.nbEnseignants = nbEnseignants;
    }


}
