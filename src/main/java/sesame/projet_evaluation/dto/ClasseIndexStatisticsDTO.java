package sesame.projet_evaluation.dto;

import java.util.HashMap;
import java.util.Map;

public class ClasseIndexStatisticsDTO {

    private String title;

    private Long classeIndex;
    private String classeName;

    private Map<String,SectionStatisticDTO> sections = new HashMap<>();

    public ClasseIndexStatisticsDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getClasseIndex() {
        return classeIndex;
    }

    public void setClasseIndex(Long classeIndex) {
        this.classeIndex = classeIndex;
    }

    public Map<String, SectionStatisticDTO> getSections() {
        return sections;
    }

    public void setSections(Map<String, SectionStatisticDTO> sections) {
        this.sections = sections;
    }

    public String getClasseName() {
        return classeName;
    }

    public void setClasseName(String classeName) {
        this.classeName = classeName;
    }
}
