package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Matiere;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.repository.ClassRepository;
import sesame.projet_evaluation.repository.MatiereRepository;
import sesame.projet_evaluation.repository.UserRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/matiere")
public class MatiereController {

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/all")
    public List<Matiere> getAll() {
        return matiereRepository.findAll();
    }

    @GetMapping("/getMatieresByClasse/{id}")
    public List<Matiere> getAllByClasse(@PathVariable("id") Long classId) {
        return matiereRepository.findMatieresByClass(classId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody Matiere matiere) {

        String nom = matiere.getNom();
        Utilisateur enseignant = userRepository.findById(matiere.getEnseignant().getId()).get();
        Classe classe = classRepository.findById(matiere.getClasse().getId()).get();
        String semestre = matiere.getSemestre();

        if(matiereRepository.existsByClasseAndEnseignantAndSemestreAndNomIgnoreCase(classe, enseignant, semestre, nom)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Matiere already exists");
        } else {
            Matiere savedMatiere = matiereRepository.save(matiere);
            return ResponseEntity.status(HttpStatus.OK).body(savedMatiere);        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        matiereRepository.deleteById(id);
    }

}
