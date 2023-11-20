package sesame.projet_evaluation.entities.MoyenneClasses;

public class MoyenneCours {

    private String courseName;

    private String enseignant;

    private Double score;

    public MoyenneCours() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
