package sesame.projet_evaluation.dto;


import sesame.projet_evaluation.entities.Classe;

import java.util.Date;

public class EtudiantDTO {

    private Long id;

    private String email;

    private Date dateSoumission;

    private Classe classe;

    private Long idSoumission;

    public EtudiantDTO() {
    }

    public EtudiantDTO(Long id, String email, Date dateSoumission, Classe classe, Long idSoumission) {
        this.id = id;
        this.email = email;
        this.dateSoumission = dateSoumission;
        this.classe = classe;
        this.idSoumission = idSoumission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(Date dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Long getIdSoumission() {
        return idSoumission;
    }

    public void setIdSoumission(Long idSoumission) {
        this.idSoumission = idSoumission;
    }
}
