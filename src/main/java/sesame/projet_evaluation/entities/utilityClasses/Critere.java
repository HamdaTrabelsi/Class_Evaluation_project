package sesame.projet_evaluation.entities.utilityClasses;

public class Critere {

    private String critereIntdex;
    private String titre;

    private String reponse;

    public String getCritereIntdex() {
        return critereIntdex;
    }

    public void setCritereIntdex(String critereIntdex) {
        this.critereIntdex = critereIntdex;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Critere(String nom, String reponse) {
        this.titre = nom;
        this.reponse = reponse;
    }
}
