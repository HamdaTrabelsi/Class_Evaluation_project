package sesame.projet_evaluation.entities;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Utilisateur implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Long id;

    //@NotBlank
    @Size(max = 50)
    private String username;

    private String email;

//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
//    @JoinColumn(name = "photo_id", referencedColumnName = "id")
//    @JsonIgnoreProperties({"user"})
//    private Photo photo;

    @ManyToOne(optional = true)
    @JoinColumn(columnDefinition="integer", name="departement_id")
    private Departement departement;

    @ManyToOne(optional = true)
    @JoinColumn(columnDefinition="integer", name="classe_id")
    private Classe classe;

    @Size(max = 50)
    private String firstname;

    @Size(max = 50)
    private String lastname;

    private String identifiant;

    private String codePostal;

    private String description;

    private String linkedInUrl;

    //@NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 200)
    private String adresse;


    private boolean actif;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public Utilisateur() {
        super();
    }


    public Utilisateur(String username, String email, String firstname, String lastname, String identifiant, String codePostal, String description, String linkedInUrl, String adresse, String password) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.identifiant = identifiant;
        this.codePostal = codePostal;
        this.description = description;
        this.linkedInUrl = linkedInUrl;
        this.password = password;
        this.adresse = adresse;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}

