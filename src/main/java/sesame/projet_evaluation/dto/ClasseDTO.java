package sesame.projet_evaluation.dto;

import sesame.projet_evaluation.entities.Classe;

public class ClasseDTO {
    private Long id;
    private String nom;

    public ClasseDTO() {
    }

    public ClasseDTO(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public static ClasseDTO fromEntity(Classe classe) {
        return new ClasseDTO(classe.getId(), classe.getNom());
    }

    public Classe toEntity() {
        Classe classe = new Classe();
        classe.setId(this.id);
        classe.setNom(this.nom);
        return classe;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
