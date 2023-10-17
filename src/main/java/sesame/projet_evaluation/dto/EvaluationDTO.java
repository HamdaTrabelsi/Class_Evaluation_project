package sesame.projet_evaluation.dto;

import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.entities.utilityClasses.Formulaire;

import java.util.Date;

public class EvaluationDTO {
    private Long id;
    private String titre;
    private String anneeUniversitaire;
    private String semestre;
    private Date creationDate;
    private Date limitDate;
    private boolean actif;
    private ClasseDTO classe;
    private Formulaire formulaire;

    // Constructors, getters, and setters

    public EvaluationDTO() {
    }

    public EvaluationDTO(Long id, String titre, String anneeUniversitaire, String semestre, Date creationDate, Date limitDate, boolean actif, ClasseDTO classe, Formulaire formulaire) {
        this.id = id;
        this.titre = titre;
        this.anneeUniversitaire = anneeUniversitaire;
        this.semestre = semestre;
        this.creationDate = creationDate;
        this.limitDate = limitDate;
        this.actif = actif;
        this.classe = classe;
        this.formulaire = formulaire;
    }

    public static EvaluationDTO fromEntity(Evaluation evaluation) {
        ClasseDTO classeDTO = (evaluation.getClasse() != null) ? ClasseDTO.fromEntity(evaluation.getClasse()) : null;
        return new EvaluationDTO(
                evaluation.getId(),
                evaluation.getTitre(),
                evaluation.getAnneeUniversitaire(),
                evaluation.getSemestre(),
                evaluation.getCreationDate(),
                evaluation.getLimitDate(),
                evaluation.isActif(),
                classeDTO,
                evaluation.getFormulaire()
        );
    }

    public Evaluation toEntity() {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(this.id);
        evaluation.setTitre(this.titre);
        evaluation.setAnneeUniversitaire(this.anneeUniversitaire);
        evaluation.setSemestre(this.semestre);
        evaluation.setCreationDate(this.creationDate);
        evaluation.setLimitDate(this.limitDate);
        evaluation.setActif(this.actif);

        if (this.classe != null) {
            evaluation.setClasse(this.classe.toEntity());
        }

        evaluation.setFormulaire(this.formulaire);
        return evaluation;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public ClasseDTO getClasse() {
        return classe;
    }

    public void setClasse(ClasseDTO classe) {
        this.classe = classe;
    }

    public Formulaire getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(Formulaire formulaire) {
        this.formulaire = formulaire;
    }
}
