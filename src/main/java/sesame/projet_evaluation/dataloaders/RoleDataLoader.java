package sesame.projet_evaluation.dataloaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sesame.projet_evaluation.entities.Role;
import sesame.projet_evaluation.repository.RoleRepository;
import sesame.projet_evaluation.utils.ERole;

@Component
public class RoleDataLoader implements ApplicationRunner {
    RoleRepository roleRepository;

    @Autowired
    public void DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(ApplicationArguments args){
        if(roleRepository.findAll().size()==0){
            roleRepository.save(new Role(ERole.ROLE_SUPER_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_ENSEIGNANT));
            roleRepository.save(new Role(ERole.ROLE_ETUDIANT));
        }
    }
}
