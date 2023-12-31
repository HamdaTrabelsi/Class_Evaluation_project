package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.dto.EvaluationDTO;
import sesame.projet_evaluation.dto.StudentEvaluationDTO;
import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.entities.Soumission;
import sesame.projet_evaluation.exception.ResourceNotFoundException;
import sesame.projet_evaluation.repository.EvaluationRepository;
import sesame.projet_evaluation.repository.SoumissionRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private SoumissionRepository soumissionRepository;

    @PostMapping("/save")
    public Evaluation save(@RequestBody EvaluationDTO evaluation) {
        evaluation.setActif(true);
        return evaluationRepository.save(evaluation.toEntity());
    }

    @GetMapping("/list")
    public List<Evaluation> get() {
        return evaluationRepository.findAll();
    }

    @GetMapping("/list/classe/{classeId}/student/{studentId}")
    public List<StudentEvaluationDTO> getStudentEvaluations(@PathVariable Long classeId, @PathVariable Long studentId) {
        List<Evaluation> evaluations = evaluationRepository.getEvaluationByClasse(classeId);
        List<StudentEvaluationDTO> studentEvaluationDTOS = new ArrayList<>();
        evaluations.forEach(evaluation -> {
            List<Soumission> soumissions = soumissionRepository.getSoumissionByEvaluationAndUtilisateur(evaluation.getId(), studentId);
            Soumission soumission = soumissions.size() > 0 ? soumissions.get(0) : null;
            StudentEvaluationDTO studentEvaluationDTO = new StudentEvaluationDTO(evaluation, soumission);
            studentEvaluationDTOS.add(studentEvaluationDTO);
        });
        return studentEvaluationDTOS;
    }

    @GetMapping("/find/{evaluationId}/{userId}")
    public ResponseEntity<EvaluationDTO> getSoumissionEvaluationById(@PathVariable Long evaluationId, @PathVariable Long userId) {
        List<Soumission> soumission = soumissionRepository.getSoumissionByEvaluationAndUtilisateur(evaluationId, userId);
        if (soumission.size() > 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(EvaluationDTO.fromEntity(evaluationRepository.findById(evaluationId).get()));
    }

    @GetMapping("/getEvaluationById/{evaluationId}")
    public Evaluation getEvaluationById(@PathVariable Long evaluationId) throws ResourceNotFoundException {
        return evaluationRepository.findById(evaluationId).orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + evaluationId));
    }
}
