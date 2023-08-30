package sesame.projet_evaluation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sesame.projet_evaluation.dto.UserDetailsImpl;
import sesame.projet_evaluation.entities.utilisateur;
import sesame.projet_evaluation.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        utilisateur utilisateur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(utilisateur);
    }

    public utilisateur updateUser(utilisateur utilisateur)	{
        return userRepository.save(utilisateur);

    }
    public utilisateur findOne(long id){
        return userRepository.findById(id).get();
    }


}