package sesame.projet_evaluation.payload.request;

import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Departement;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.Set;

public class SignUpRequest {
    @Size(min = 3, max = 50)
    private String username;

    //@NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String firstname;

    @Size(max = 50)
    private String lastname;

    private String identifiant;

    private String codePostal;

    private String description;

    private String linkedInUrl;

    @Size(max = 200)
    private String adresse;

    private Set<String> roles;

    //@NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Departement departement;

    private Classe classe;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    @Override
    public String toString() {
        return "SignupRequest [username=" + username + ", email=" + email + ", lastname=" + lastname + ", roles=" + roles
                + ", password=" + password + ", societe= "+ "]";
    }


}
