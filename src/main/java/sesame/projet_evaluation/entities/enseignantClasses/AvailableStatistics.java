package sesame.projet_evaluation.entities.enseignantClasses;

import sesame.projet_evaluation.entities.Classe;

import java.util.Set;

public class AvailableStatistics {

    private String anneeUniversitaire;

    private Set<Classe> classeSet;

    public AvailableStatistics() {
    }

    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public Set<Classe> getClasseSet() {
        return classeSet;
    }

    public void setClasseSet(Set<Classe> classeSet) {
        this.classeSet = classeSet;
    }
}
