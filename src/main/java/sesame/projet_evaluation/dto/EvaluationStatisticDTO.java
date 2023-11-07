package sesame.projet_evaluation.dto;

import java.util.HashSet;
import java.util.Set;

public class EvaluationStatisticDTO {

    private Long id;

    private String title;

    private Set<SectionStatisticDTO> sectionsStats = new HashSet<>();

    public EvaluationStatisticDTO() {
    }

    public EvaluationStatisticDTO(Long id, String title, Set<SectionStatisticDTO> sectionsStats) {
        this.id = id;
        this.title = title;
        this.sectionsStats = sectionsStats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SectionStatisticDTO> getSectionsStats() {
        return sectionsStats;
    }

    public void setSectionsStats(Set<SectionStatisticDTO> sectionsStats) {
        this.sectionsStats = sectionsStats;
    }
}
