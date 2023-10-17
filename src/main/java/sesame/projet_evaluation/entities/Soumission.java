package sesame.projet_evaluation.entities;

import org.hibernate.annotations.Type;
import sesame.projet_evaluation.entities.utilityClasses.Formulaire;

import javax.persistence.*;

@Entity
public class Soumission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Long id;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="evaluation_id")
    private Evaluation evaluation;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Formulaire formulaire;

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
}
