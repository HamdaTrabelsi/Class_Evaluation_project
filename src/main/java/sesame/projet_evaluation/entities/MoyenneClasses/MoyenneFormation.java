package sesame.projet_evaluation.entities.MoyenneClasses;

import java.util.ArrayList;
import java.util.List;

public class MoyenneFormation {

    private String name;

    private List<MoyenneCours> moyennesCours = new ArrayList<>();

    private Double indiceRetour;

    public MoyenneFormation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MoyenneCours> getMoyennesCours() {
        return moyennesCours;
    }

    public void setMoyennesCours(List<MoyenneCours> moyennesCours) {
        this.moyennesCours = moyennesCours;
    }

    public Double getIndiceRetour() {
        return indiceRetour;
    }

    public void setIndiceRetour(Double indiceRetour) {
        this.indiceRetour = indiceRetour;
    }
}
