package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.dto.CritereStatisticDTO;
import sesame.projet_evaluation.dto.QuestionStatisticDTO;
import sesame.projet_evaluation.dto.SectionStatisticDTO;
import sesame.projet_evaluation.entities.Soumission;
import sesame.projet_evaluation.entities.utilityClasses.Critere;
import sesame.projet_evaluation.entities.utilityClasses.Formulaire;
import sesame.projet_evaluation.entities.utilityClasses.Question;
import sesame.projet_evaluation.entities.utilityClasses.Section;
import sesame.projet_evaluation.repository.SoumissionRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/soumission")
public class SoumissionController {

    @Autowired
    private SoumissionRepository soumissionRepository;


    @PostMapping("/save")
    public Soumission save(@RequestBody Soumission soumission) {
        soumission.setDateCreation(new Date());
        return soumissionRepository.save(soumission);
    }

    @GetMapping("/getSoumissionByEvaluation/{evaluationId}")
    public Map<String, SectionStatisticDTO> getSoumissionByEvaluation(@PathVariable Long evaluationId) {
        List<Soumission> soumissionList = soumissionRepository.getSoumissionByEvaluation(evaluationId);

        List<Formulaire> formulaires = new ArrayList<>();
        soumissionList.forEach(soumission -> {
            formulaires.add(soumission.getFormulaire());
        });

        Map<String, SectionStatisticDTO> sectionStatisticDTOS = new HashMap<>();

        for(Formulaire f : formulaires) {
            for(Section s : f.getSections()){
                for(Question q : s.getQuestions()){
                    for (Critere c : q.getCriteres()){
                        sectionStatisticDTOS = addToStats(s.getSectionName(),q.getQuestionText(),c.getTitre(), c.getReponse(),sectionStatisticDTOS);
                    }
                }
            }
        }

        System.out.println(sectionStatisticDTOS);

        return sectionStatisticDTOS;
    }




    public Map<String, SectionStatisticDTO>  addToStats(String sectionName, String questionName, String critereName, String critereResponse, Map<String, SectionStatisticDTO> sectionStatisticDTOS) {
        SectionStatisticDTO sectionStatisticDTO = sectionStatisticDTOS.get(sectionName);

        if (sectionStatisticDTO != null) {
            QuestionStatisticDTO questionStatisticDTO = sectionStatisticDTO.getQuestions().get(questionName);

            if (questionStatisticDTO != null) {

                CritereStatisticDTO critereStatisticDTO = questionStatisticDTO.getCriteres().get(critereName);

                if (critereStatisticDTO != null) {

                    if (critereStatisticDTO.getResponses().containsKey(critereResponse)) {
                        int count = critereStatisticDTO.getResponses().get(critereResponse);
                        critereStatisticDTO.getResponses().put(critereResponse, count + 1);
                        sectionStatisticDTOS.get(sectionName).getQuestions().get(questionName).getCriteres()
                                .get(critereName).setResponses(critereStatisticDTO.getResponses());

                    } else {
                        critereStatisticDTO.getResponses().put(critereResponse, 1);
                        sectionStatisticDTOS.get(sectionName).getQuestions().get(questionName).getCriteres()
                                .get(critereName).setResponses(critereStatisticDTO.getResponses());
                    }

                } else {
                    CritereStatisticDTO newCritereStatisticDTO = createEmptyCritere(critereName, critereResponse);
                    sectionStatisticDTOS.get(sectionName).getQuestions().get(questionName).getCriteres().put(critereName, newCritereStatisticDTO);
                }

             } else {
                QuestionStatisticDTO newQuestionStatisticDTO = createEmptyQuestion(questionName,critereName,critereResponse);
                sectionStatisticDTOS.get(sectionName).getQuestions().put(questionName, newQuestionStatisticDTO);
            }
        } else {
            SectionStatisticDTO newSectionStatisticDTO = createEmptySection(sectionName,questionName,critereName,critereResponse);

            sectionStatisticDTOS.put(sectionName, newSectionStatisticDTO);
        }

        return sectionStatisticDTOS;
    }

    public SectionStatisticDTO createEmptySection(String sectionName, String questionName, String critereName, String critereResponse) {
        SectionStatisticDTO newSectionStatisticDTO = new SectionStatisticDTO();
        newSectionStatisticDTO.setSectionName(sectionName);

        QuestionStatisticDTO newQuestionStatisticDTO = new QuestionStatisticDTO();
        newQuestionStatisticDTO.setQuestionText(questionName);
        newSectionStatisticDTO.getQuestions().put(questionName,newQuestionStatisticDTO);

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setTitle(critereName);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        newQuestionStatisticDTO.getCriteres().put(critereName, newCritereStatisticDTO);

        return newSectionStatisticDTO;
    }

    public QuestionStatisticDTO createEmptyQuestion(String questionName, String critereName, String critereResponse) {

        QuestionStatisticDTO newQuestionStatisticDTO = new QuestionStatisticDTO();
        newQuestionStatisticDTO.setQuestionText(questionName);

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setTitle(critereName);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        newQuestionStatisticDTO.getCriteres().put(critereName, newCritereStatisticDTO);

        return newQuestionStatisticDTO;
    }

    public CritereStatisticDTO createEmptyCritere(String critereName, String critereResponse) {

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setTitle(critereName);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        return newCritereStatisticDTO;
    }
}