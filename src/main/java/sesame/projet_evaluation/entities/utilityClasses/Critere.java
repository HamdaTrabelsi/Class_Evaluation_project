package sesame.projet_evaluation.entities.utilityClasses;

public class Critere {

    private String critereIndex;
    private String titre;

    private String reponse;

    public String getCritereIndex() {
        return critereIndex;
    }

    public void setCritereIndex(String critereIndex) {
        this.critereIndex = critereIndex;
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
