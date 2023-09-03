package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.entities.Departement;
import sesame.projet_evaluation.repository.DepartementRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/departement")
public class DepartmementController {

    @Autowired
    private DepartementRepository departementRepository;

    @GetMapping("/all")
    public List<Departement> getAll(){
        return departementRepository.findAll();
    }

    @PostMapping("/save")
    public Departement add(@RequestBody Departement departement) {
        return departementRepository.save(departement);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        departementRepository.deleteById(id);
    }
}
