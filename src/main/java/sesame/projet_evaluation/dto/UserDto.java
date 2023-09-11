package sesame.projet_evaluation.dto;

import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Role;
import sesame.projet_evaluation.entities.Utilisateur;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class UserDto {


    private Long id;

    private String email;

    private String username;

    private String firstname;

    private String lastname;

    private Classe classe;

    private String identifiant;

    private String codePostal;

    private String description;

    private String linkedInUrl;

    @Size(max = 200)
    private String adresse;

    private boolean actif;

    private Set<Role> roles = new HashSet<>();

    public UserDto() {
    }

    public UserDto(Long id, String email, String username, String firstname, String lastname, Classe classe, String identifiant, String codePostal, String description, String linkedInUrl, String adresse, boolean actif, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.classe = classe;
        this.identifiant = identifiant;
        this.codePostal = codePostal;
        this.description = description;
        this.linkedInUrl = linkedInUrl;
        this.adresse = adresse;
        this.actif = actif;
        this.roles = roles;
    }

    public static UserDto fromClass(Utilisateur user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstName());
        userDto.setLastname(user.getLastname());
        userDto.setClasse(user.getClasse());
        userDto.setIdentifiant(user.getIdentifiant());
        userDto.setCodePostal(user.getCodePostal());
        userDto.setDescription(user.getDescription());
        userDto.setLinkedInUrl(user.getLinkedInUrl());
        userDto.setAdresse( user.getAdresse());
        userDto.setActif( user.isActif());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
