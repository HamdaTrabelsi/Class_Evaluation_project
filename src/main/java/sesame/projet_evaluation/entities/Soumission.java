package sesame.projet_evaluation.entities;

import org.hibernate.annotations.Type;
import sesame.projet_evaluation.entities.utilityClasses.Formulaire;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Soumission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Long id;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="evaluation_id")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="utilisateur_id")
    private Utilisateur utilisateur;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Formulaire formulaire;

    private Date dateCreation;


    public Soumission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Formulaire getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(Formulaire formulaire) {
        this.formulaire = formulaire;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
