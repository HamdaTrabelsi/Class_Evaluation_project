package sesame.projet_evaluation.entities.utilityClasses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Formulaire {

    private Set<Section> sections;

    public Formulaire(Set<Section> sections) {
        this.sections = sections;
    }

    public Formulaire() {
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}
