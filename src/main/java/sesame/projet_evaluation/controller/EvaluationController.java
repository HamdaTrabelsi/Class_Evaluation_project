package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.repository.EvaluationRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @PostMapping("/save")
    public Evaluation save(@RequestBody Evaluation evaluation){
        return evaluationRepository.save(evaluation);
    }
}
