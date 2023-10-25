package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.dto.UserDto;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.payload.response.MessageResponse;
import sesame.projet_evaluation.repository.RoleRepository;
import sesame.projet_evaluation.repository.UserRepository;
import sesame.projet_evaluation.utils.ERole;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class utilisateurController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/list")
    public List<UserDto> getAll(){
        List<UserDto> userDtos = new ArrayList<>();
        List<Utilisateur> users = userRepository.findAll();

        users.forEach(user -> {
            userDtos.add(UserDto.fromClass(user));
        });


        return userDtos;
    }

    @GetMapping("/getById/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        Utilisateur user = userRepository.findById(id).get();

        return UserDto.fromClass(user);
    }

    @GetMapping("/getByRole")
    public List<UserDto> getUsersByRole(@RequestParam String roleName) {

        List<UserDto> userDtos = new ArrayList<>();

        userRepository.findByRoleName(ERole.valueOf(roleName)).forEach(user -> {
            userDtos.add(UserDto.fromClass(user));
        });

        return userDtos;
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> UpdateUser(@PathVariable(value = "id") Long idUser, @Valid @RequestBody Utilisateur utilisateur) {

        // update user's account
        Utilisateur U = userRepository.findById(idUser).get();
        if (utilisateur.getUsername() != null) {
            U.setUsername(utilisateur.getUsername());
        }
        if (utilisateur.getEmail() != null) {
            if (!Objects.equals(utilisateur.getEmail(), U.getEmail())) {
                if (userRepository.checkIfEmailExistsWhenUpdating(idUser, utilisateur.getEmail())>0) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Email already in use !"));
                } else {
                    U.setEmail(utilisateur.getEmail());
                }
            }
        }
        if (utilisateur.getIdentifiant() != null) {
            U.setIdentifiant(utilisateur.getIdentifiant());
        }

        if (utilisateur.getFirstName() != null) {
            U.setFirstName(utilisateur.getFirstName());
        }

        if (utilisateur.getLastname() != null) {
            U.setLastname(utilisateur.getLastname());
        }

        if (utilisateur.getAdresse() != null) {
            U.setAdresse(utilisateur.getAdresse());
        }

        if (utilisateur.getCodePostal() != null) {
            U.setCodePostal(utilisateur.getCodePostal());
        }

        if (utilisateur.getDescription() != null) {
            U.setDescription(utilisateur.getDescription());
        }

        if (utilisateur.getPassword() != null && !utilisateur.getPassword().isEmpty()) {
            U.setPassword(encoder.encode(utilisateur.getPassword()));
        }

        userRepository.save(U);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}
