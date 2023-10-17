package sesame.projet_evaluation.entities.utilityClasses;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

public class Question {


    private String questionIndex;

    private String questionText;

    private Set<Critere> criteres;

    public Question() {
    }


    public String getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(String questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Set<Critere> getCriteres() {
        return criteres;
    }

    public void setCriteres(Set<Critere> criteres) {
        this.criteres = criteres;
    }
}
