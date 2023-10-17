package sesame.projet_evaluation.dto;

import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.entities.Soumission;

import java.util.Date;

public class StudentEvaluationDTO {

    private Long evaluationId;

    private Long reponseId;

    private Date dateReponse;

    private String titre;

    private Date dateCreation;

    private Date dateLimite;

    private boolean actif;

    private boolean repondu;

    public StudentEvaluationDTO() {
    }

    public StudentEvaluationDTO(Evaluation evaluation, Soumission soumission) {
        this.evaluationId = evaluation.getId();
        this.titre = evaluation.getTitre();
        this.dateCreation = evaluation.getCreationDate();
        this.dateLimite = evaluation.getLimitDate();
        this.actif = evaluation.isActif();

        // Check if soumission is not null to determine the value of repondu
        this.repondu = (soumission != null);
        this.reponseId = (soumission != null) ? soumission.getId() : null;
        this.dateReponse = (soumission != null) ? soumission.getDateCreation() : null;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getReponseId() {
        return reponseId;
    }

    public void setReponseId(Long reponseId) {
        this.reponseId = reponseId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(Date dateLimite) {
        this.dateLimite = dateLimite;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isRepondu() {
        return repondu;
    }

    public void setRepondu(boolean repondu) {
        this.repondu = repondu;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }
}
