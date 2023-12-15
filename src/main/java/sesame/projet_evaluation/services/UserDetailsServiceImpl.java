package sesame.projet_evaluation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sesame.projet_evaluation.dto.UserDetailsImpl;
import sesame.projet_evaluation.entities.Role;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.repository.RoleRepository;
import sesame.projet_evaluation.repository.UserRepository;
import sesame.projet_evaluation.utils.ERole;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(utilisateur);
    }

    public Utilisateur updateUser(Utilisateur utilisateur)	{
        return userRepository.save(utilisateur);

    }
    public Utilisateur findOne(long id){
        return userRepository.findById(id).get();
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
