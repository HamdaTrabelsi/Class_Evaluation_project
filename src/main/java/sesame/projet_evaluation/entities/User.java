package sesame.projet_evaluation.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;


@Entity
public class User implements Serializable {

    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private Long id;

    //@NotBlank
    @Size(max = 20)
    private String username;

    //@Email
    private String email;

//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
//    @JoinColumn(name = "photo_id", referencedColumnName = "id")
//    @JsonIgnoreProperties({"user"})
//    private Photo photo;


    //@NotBlank
    @Size(max = 20)
    private String lastname;

    //@NotBlank
    @Size(max = 120)
    private String password;


    private boolean actif;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();









    public User() {
        super();
    }




    public User(@NotBlank @Size(max = 20) String username, @Email String email,
                       @NotBlank @Size(max = 20) String lastname,@NotBlank @Size(max = 120) String password, boolean actif, Set<Role> roles) {
        super();
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.password = password;
        this.actif = actif;
        this.roles = roles;
    }


    public User(Long id, @NotBlank @Size(max = 20) String username, @Email String email,
                       @NotBlank @Size(max = 20) String lastname, @NotBlank @Size(max = 120) String password, boolean actif,
                       Set<Role> roles) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.password = password;
        this.actif = actif;
        this.roles = roles;
    }


    public User(@NotBlank @Size(max = 20) String username, @Email String email,
                       @NotBlank @Size(max = 20) String lastname, @NotBlank @Size(max = 20)  String password) {
        super();
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.password = password;
    }


    public User(@NotBlank @Size(max = 20) String username, @Email String email,
                @NotBlank @Size(max = 20) String lastname,
                @NotBlank @Size(max = 120) String password, boolean actif) {
        super();
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.password = password;
        this.actif = actif;
    }
}

