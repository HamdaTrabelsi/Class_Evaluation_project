package sesame.projet_evaluation.dto;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionStatisticDTO {

    private String sectionName;

    private Map<String,QuestionStatisticDTO> questions = new HashMap<>();

    public SectionStatisticDTO() {
    }

    public SectionStatisticDTO(String sectionName, Map<String, QuestionStatisticDTO> questions) {
        this.sectionName = sectionName;
        this.questions = questions;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Map<String, QuestionStatisticDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, QuestionStatisticDTO> questions) {
        this.questions = questions;
    }
}