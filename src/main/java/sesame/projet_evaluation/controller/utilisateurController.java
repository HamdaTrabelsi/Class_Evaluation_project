package sesame.projet_evaluation.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sesame.projet_evaluation.dto.UserDto;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Role;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.payload.request.SignUpRequest;
import sesame.projet_evaluation.payload.response.MessageResponse;
import sesame.projet_evaluation.repository.ClassRepository;
import sesame.projet_evaluation.repository.RoleRepository;
import sesame.projet_evaluation.repository.UserRepository;
import sesame.projet_evaluation.services.UserDetailsServiceImpl;
import sesame.projet_evaluation.utils.ERole;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static sesame.projet_evaluation.utils.UtilMethods.readExcelFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class utilisateurController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

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

    @PostMapping("/importer/etudiants")
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file,@RequestParam Long classeId) {
        try {
            // Read Excel file and convert rows to SignUpRequest objects
            List<SignUpRequest> signUpRequests = readExcelFile(file);
            Classe classe = classRepository.findById(classeId).get();
            // Process the list of SignUpRequest objects as needed

            List <Utilisateur> savedUsers = new ArrayList<>();
            signUpRequests.forEach(etudiant -> {
                int exists = userRepository.checkIfEmailExists(etudiant.getEmail());
                if(exists > 0){
                    System.out.println("etudiant "+ etudiant.getEmail() +" exists");
                    Optional<Utilisateur> user = userRepository.findUserByEmail(etudiant.getEmail());
                    if(user.isPresent()){
                        Utilisateur updatedUser = updateUserByExcel(etudiant, user.get(), classe);
                        Utilisateur savedUser = userRepository.save(updatedUser);
                        savedUsers.add(savedUser);
                    }
                }else {
                    System.out.println("etudiant "+ etudiant.getEmail() +" does not exist");
                    Utilisateur createdUser = createUserByExcel(etudiant, classe,"etudiant");
                    Utilisateur savedUser = userRepository.save(createdUser);
                    savedUsers.add(savedUser);
                }
            });

            return ResponseEntity.ok(savedUsers.size());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getCountStats")
    private HashMap<String,Integer> getCountStats(){
        HashMap<String, Integer> countbyRole = new HashMap<>();
        Integer nbAdmin = userRepository.countUsersByRole(ERole.ROLE_ADMIN);
        Integer nbEnseignants = userRepository.countUsersByRole(ERole.ROLE_ENSEIGNANT);
        Integer nbEtudiants = userRepository.countUsersByRole(ERole.ROLE_ETUDIANT);
        Integer nbUsers = userRepository.countUsers();
        Integer nbClasses = userRepository.countClasses();
        Integer nbMatieres = userRepository.countMatiere();
        countbyRole.put("admin", nbAdmin);
        countbyRole.put("enseignant", nbEnseignants);
        countbyRole.put("etudiant", nbEtudiants);
        countbyRole.put("users", nbUsers);
        countbyRole.put("classes", nbClasses);
        countbyRole.put("matieres", nbMatieres);
        return countbyRole;
    }



    private Utilisateur updateUserByExcel(SignUpRequest signUpRequest, Utilisateur utilisateur, Classe classe) {
        utilisateur.setFirstName(signUpRequest.getFirstname());
        utilisateur.setLastname(signUpRequest.getLastname());
        utilisateur.setIdentifiant(signUpRequest.getIdentifiant());
        utilisateur.setCodePostal(signUpRequest.getCodePostal());
        utilisateur.setAdresse(signUpRequest.getAdresse());
        utilisateur.setPassword(encoder.encode(signUpRequest.getCodePostal()));
        utilisateur.setClasse(classe);
        return utilisateur;
    }

    private Utilisateur createUserByExcel(SignUpRequest signUpRequest, Classe classe, String role) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(signUpRequest.getEmail());
        utilisateur.setFirstName(signUpRequest.getFirstname());
        utilisateur.setLastname(signUpRequest.getLastname());
        utilisateur.setIdentifiant(signUpRequest.getIdentifiant());
        utilisateur.setCodePostal(signUpRequest.getCodePostal());
        utilisateur.setAdresse(signUpRequest.getAdresse());
        utilisateur.setPassword(encoder.encode(signUpRequest.getPassword()));
        utilisateur.setClasse(classe);

        Set<String> userRole = new HashSet<>();
        userRole.add(role);
        Set<Role> roles = userDetailsService.prepareRoles(userRole);
        utilisateur.setRoles(roles);

        return utilisateur;
    }



}
