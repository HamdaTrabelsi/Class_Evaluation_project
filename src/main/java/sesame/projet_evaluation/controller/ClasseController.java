package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Departement;
import sesame.projet_evaluation.repository.ClassRepository;
import sesame.projet_evaluation.repository.DepartementRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/classe")
public class ClasseController {
    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/all")
    public List<Classe> getAll(){
        return classRepository.findAll();
    }

    @PostMapping("/save")
    public Classe add(@RequestBody Classe classe) {
        return classRepository.save(classe);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        classRepository.deleteById(id);
    }
}
