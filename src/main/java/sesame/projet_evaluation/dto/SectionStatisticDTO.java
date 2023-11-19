package sesame.projet_evaluation.dto;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionStatisticDTO {

    private String sectionName;

    private String sectionIndex;

    private String enseignantName;

    private Map<String,QuestionStatisticDTO> questions = new HashMap<>();

    public SectionStatisticDTO() {
    }

    public SectionStatisticDTO(String sectionName, String sectionIndex, Map<String, QuestionStatisticDTO> questions, String enseignantName) {
        this.sectionName = sectionName;
        this.sectionIndex = sectionIndex;
        this.questions = questions;
        this.enseignantName = enseignantName;
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

    public String getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(String sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public String getEnseignantName() {
        return enseignantName;
    }

    public void setEnseignantName(String enseignantName) {
        this.enseignantName = enseignantName;
    }
}
