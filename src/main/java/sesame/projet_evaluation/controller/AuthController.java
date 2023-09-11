package sesame.projet_evaluation.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.dto.UserDetailsImpl;
import sesame.projet_evaluation.entities.Role;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.payload.request.LoginRequest;
import sesame.projet_evaluation.payload.request.SignUpRequest;
import sesame.projet_evaluation.payload.response.JwtResponse;
import sesame.projet_evaluation.payload.response.MessageResponse;
import sesame.projet_evaluation.repository.RoleRepository;
import sesame.projet_evaluation.repository.UserRepository;
import sesame.projet_evaluation.security.jwt.JwtUtils;
import sesame.projet_evaluation.services.UserDetailsServiceImpl;
import sesame.projet_evaluation.utils.ERole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getlastname(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        System.err.println(signUpRequest);


        // Create new user's account
        Utilisateur utilisateur = new Utilisateur(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getIdentifiant(),
                signUpRequest.getCodePostal(),
                signUpRequest.getDescription(),
                signUpRequest.getLinkedInUrl(),
                signUpRequest.getAdresse(),
                encoder.encode(signUpRequest.getPassword())
                );



        if(signUpRequest.getDepartement() != null){
            utilisateur.setDepartement(signUpRequest.getDepartement());
        }
        if(signUpRequest.getClasse() != null){
            utilisateur.setClasse(signUpRequest.getClasse());
        }


        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = prepareRoles(strRoles);

        utilisateur.setRoles(roles);


        userRepository.save(utilisateur);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    public Set<Role> prepareRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN).get();
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_SUPER_ADMIN":
                    case "superAdmin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "ROLE_ADMIN":
                    case "admin":
                        Role dirRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(dirRole);
                        break;
                    case "ROLE_ENSEIGNANT":
                    case "enseignant":
                        Role managRole = roleRepository.findByName(ERole.ROLE_ENSEIGNANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managRole);
                        break;
                    case "ROLE_ETUDIANT":
                    case "etudiant":
                    default:
                        Role superviseurRole = roleRepository.findByName(ERole.ROLE_ETUDIANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(superviseurRole);

                        break;
                }
            });
        }

        return roles;
    }
}
