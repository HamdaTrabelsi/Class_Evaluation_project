package sesame.projet_evaluation.entities.MoyenneClasses;

import java.util.HashMap;
import java.util.Map;

public class MoyenneBarometre {

    private String nom;

    private Map<String, Double> scores = new HashMap<>();

    private Double moyenne;

    public MoyenneBarometre() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Map<String, Double> getScores() {
        return scores;
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
    }

    public Double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }
}
