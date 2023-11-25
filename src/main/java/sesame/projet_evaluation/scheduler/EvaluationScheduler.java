package sesame.projet_evaluation.scheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sesame.projet_evaluation.services.EvaluationService;

@Component
public class EvaluationScheduler {

    private final EvaluationService evaluationService; // Inject your EvaluationService here

    public EvaluationScheduler(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    // Schedule the job every night at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateEvaluationStatus() {
        evaluationService.updateEvaluationStatus();
    }
}
