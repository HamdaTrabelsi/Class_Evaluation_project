package sesame.projet_evaluation.services;

import org.springframework.stereotype.Service;
import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.repository.EvaluationRepository;

import java.util.Date;
import java.util.List;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }
    public void updateEvaluationStatus() {
        List<Evaluation> evaluations = evaluationRepository.findAll();

        for (Evaluation evaluation : evaluations) {
            if (evaluation.getLimitDate() != null && evaluation.getLimitDate().before(new Date())) {
                evaluation.setActif(false);
                evaluationRepository.save(evaluation);
            }
        }
    }
}
