package sesame.projet_evaluation.dto;

import java.util.HashMap;
import java.util.Map;

public class CritereStatisticDTO {

    private String title;

    private Map<String, Integer> responses = new HashMap<>();

    public CritereStatisticDTO() {
    }

    public CritereStatisticDTO(String title, Map<String, Integer> responses) {
        this.title = title;
        this.responses = responses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Integer> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Integer> responses) {
        this.responses = responses;
    }
}
