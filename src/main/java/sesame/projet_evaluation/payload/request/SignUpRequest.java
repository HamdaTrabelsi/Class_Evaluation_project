package sesame.projet_evaluation.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignUpRequest {
    @Size(min = 3, max = 20)
    private String username;

    //@NotBlank
    @Size(max = 50)
    @Email
    private String email;

    // @NotBlank
    @Size(max = 20)
    private String lastname;


    private Set<String> roles;



    //@NotBlank
    @Size(min = 6, max = 40)
    private String password;

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

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }



    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SignupRequest [username=" + username + ", email=" + email + ", lastname=" + lastname + ", roles=" + roles
                + ", password=" + password + ", societe= "+ "]";
    }


}
