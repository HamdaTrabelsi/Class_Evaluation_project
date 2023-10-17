package sesame.projet_evaluation.payload.response;

import sesame.projet_evaluation.entities.Classe;

import java.util.List;

public class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String email;
        private String username;
        private String lastname;
        private List<String> roles;

        private Classe classe;

	public JwtResponse(String accessToken, Long id, String username, String email, String lastname, List<String> roles, Classe classe) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.roles = roles;
        this.classe = classe;
    }

        public String getAccessToken() {
        return token;
    }

        public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

        public String getTokenType() {
        return type;
    }

        public void setTokenType(String tokenType) {
        this.type = tokenType;
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


        public String getlastname() {
        return lastname;
    }

        public void setlastname(String lastname) {
        this.lastname = lastname;
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

        public List<String> getRoles() {
        return roles;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
}
