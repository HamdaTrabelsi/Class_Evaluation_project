package sesame.projet_evaluation.entities;

import jakarta.persistence.*;
import sesame.projet_evaluation.utils.ERole;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
        super();
    }

    public Role(ERole name) {
        super();
        this.name = name;
    }

    public Role(ERole name, User user) {
        super();
        this.name = name;

    }

    public Role(Integer id, ERole name, User user) {
        super();
        this.id = id;
        this.name = name;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + "]";
    }

}
