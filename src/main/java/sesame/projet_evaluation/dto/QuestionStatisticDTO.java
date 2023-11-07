package sesame.projet_evaluation.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionStatisticDTO {

    private String questionText;

    private Map<String, CritereStatisticDTO> criteres = new HashMap<>();

    public QuestionStatisticDTO() {
    }

    public QuestionStatisticDTO(String questionText, Map<String, CritereStatisticDTO> criteres) {
        this.questionText = questionText;
        this.criteres = criteres;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Map<String, CritereStatisticDTO> getCriteres() {
        return criteres;
    }

    public void setCriteres(Map<String, CritereStatisticDTO> criteres) {
        this.criteres = criteres;
    }
}
