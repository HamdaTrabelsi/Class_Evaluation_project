package sesame.projet_evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sesame.projet_evaluation.dto.*;
import sesame.projet_evaluation.entities.Classe;
import sesame.projet_evaluation.entities.Evaluation;
import sesame.projet_evaluation.entities.MoyenneClasses.MoyenneBarometre;
import sesame.projet_evaluation.entities.MoyenneClasses.MoyenneCours;
import sesame.projet_evaluation.entities.MoyenneClasses.MoyenneEval;
import sesame.projet_evaluation.entities.MoyenneClasses.MoyenneFormation;
import sesame.projet_evaluation.entities.Soumission;
import sesame.projet_evaluation.entities.Utilisateur;
import sesame.projet_evaluation.entities.enseignantClasses.AvailableStatistics;
import sesame.projet_evaluation.entities.utilityClasses.Critere;
import sesame.projet_evaluation.entities.utilityClasses.Formulaire;
import sesame.projet_evaluation.entities.utilityClasses.Question;
import sesame.projet_evaluation.entities.utilityClasses.Section;
import sesame.projet_evaluation.exception.ResourceNotFoundException;
import sesame.projet_evaluation.repository.ClassRepository;
import sesame.projet_evaluation.repository.EvaluationRepository;
import sesame.projet_evaluation.repository.SoumissionRepository;
import sesame.projet_evaluation.repository.UserRepository;
import sesame.projet_evaluation.utils.ERole;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/soumission")
public class SoumissionController {

    @Autowired
    private SoumissionRepository soumissionRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

   // private static final DecimalFormat df = new DecimalFormat("0.00");

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.UK);
    DecimalFormat df = (DecimalFormat)nf;

    @PostMapping("/save")
    public Soumission save(@RequestBody Soumission soumission) {
        soumission.setDateCreation(new Date());
        return soumissionRepository.save(soumission);
    }

    @GetMapping("/getById/{id}")
    public Soumission find(@PathVariable Long id) throws ResourceNotFoundException {
        return soumissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Soumission not found with id: " + id));
    }

    @GetMapping("/getStudentByEvaluation/{id}")
    public List<EtudiantDTO> getStudentsByEvaluation(@PathVariable Long id) {
        List<EtudiantDTO> etudiants = soumissionRepository.getEtudiantsByEvaluation(id);
        return etudiants;
    }


    @GetMapping("/getSoumissionByEvaluation/{evaluationId}")
    public Map<String, SectionStatisticDTO> getSoumissionByEvaluation(@PathVariable Long evaluationId) {
        List<Soumission> soumissionList = soumissionRepository.getSoumissionByEvaluation(evaluationId);

        List<Formulaire> formulaires = new ArrayList<>();
        soumissionList.forEach(soumission -> {
            formulaires.add(soumission.getFormulaire());
        });

        Map<String, SectionStatisticDTO> sectionStatisticDTOS = new HashMap<>();

        for (Formulaire f : formulaires) {
            for (Section s : f.getSections()) {
                for (Question q : s.getQuestions()) {
                    for (Critere c : q.getCriteres()) {
                        Utilisateur enseignant = userRepository.findById(s.getEnseignantId()).get();
                        String enseignantName = enseignant.getLastname() + " " + enseignant.getFirstName();
                        sectionStatisticDTOS = addToStats(s.getSectionId(), s.getSectionName(), q.getQuestionIndex(), q.getQuestionText(), c.getCritereIndex(), c.getTitre(), c.getReponse(), sectionStatisticDTOS, enseignantName);
                    }
                }
            }
        }

        System.out.println(sectionStatisticDTOS);

        return sectionStatisticDTOS;
    }

    @GetMapping("/getNumberOfSoumissionForClasse/{evaluationId}")
    public Map<String, Integer> getNumberOfSoumissionsForClasse(@PathVariable Long evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();
        Integer totalEtudiantsClasse = userRepository.countClasseStudents(evaluation.getClasse().getId(), ERole.ROLE_ETUDIANT);
        Integer nbrReponses = soumissionRepository.countSoumissionForEvaluation(evaluationId, ERole.ROLE_ETUDIANT);
        Integer nonRepondus = totalEtudiantsClasse - nbrReponses;

        Map<String, Integer> statsReponses = new HashMap<>();
        statsReponses.put("total", totalEtudiantsClasse);
        statsReponses.put("reponses", nbrReponses);
        statsReponses.put("nonRepondus", nonRepondus);
        return statsReponses;
    }

    @GetMapping("/getMoyennesEvaluation/{evaluationId}")
    public MoyenneEval getMoyennesEvaluation(@PathVariable Long evaluationId) {
        List<Soumission> soumissionList = soumissionRepository.getSoumissionByEvaluation(evaluationId);

        MoyenneEval moyenneEval = calculateMoyenne(soumissionList);

        return moyenneEval;
    }

    @GetMapping("/getEnseignantAnneesUniversitaires/{enseignantId}")
    public List<AvailableStatistics> getEnseignatAnneesUniversitaires(@PathVariable Long enseignantId) {
        List<Classe> classes = userRepository.getAnneeUniversitaireAndClasseByEnseignantId(enseignantId);

        List<AvailableStatistics> availableStatisticsList = new ArrayList<>();
        classes.forEach(classe -> {
            OptionalInt index = IntStream.range(0, availableStatisticsList.size())
                    .filter(i -> availableStatisticsList.get(i).getAnneeUniversitaire().equals(classe.getAnneeUniversitaire()))
                    .findFirst();
            if (index.isEmpty()) {
                AvailableStatistics availableStatistics = new AvailableStatistics();
                availableStatistics.setAnneeUniversitaire(classe.getAnneeUniversitaire());
                Set<Classe> classeSet = new HashSet<>();
                classeSet.add(classe);
                availableStatistics.setClasseSet(classeSet);
                availableStatisticsList.add(availableStatistics);
            } else {
                Set<Classe> classeSet = availableStatisticsList.get(index.getAsInt()).getClasseSet();
                classeSet.add(classe);
                availableStatisticsList.get(index.getAsInt()).setClasseSet(classeSet);
            }
        });
        return availableStatisticsList;
    }

    @GetMapping("/getEnseignantSoumissions/{enseignantId}")
    public List<Soumission> getEnseignantSoumissions(@PathVariable Long enseignantId, @RequestParam String anneeUniversitaire) {
        List<Classe> enseignantClasses = soumissionRepository.getClassesByEnseignant(enseignantId, anneeUniversitaire);
        List<Evaluation> evaluations = new ArrayList<>();
        enseignantClasses.forEach(classe -> {
            List<Evaluation> retievedEvaluations = soumissionRepository.getEvaluationsByClasse(classe.getId());
            if (!retievedEvaluations.isEmpty()) {
                evaluations.addAll(retievedEvaluations);
            }
        });

        List<Soumission> soumissionList = new ArrayList<>();

        evaluations.forEach(evaluation -> {
            List<Soumission> evalSoumissions = soumissionRepository.getSoumissionByEvaluation(evaluation.getId());
            if (evalSoumissions.size() > 0) {
                soumissionList.addAll(evalSoumissions);
            }
        });

        return soumissionList;
    }

    @GetMapping("/getStatistiquesEnseignantByIdAndAnnee/{enseignantId}")
    public Map<Long, ClasseIndexStatisticsDTO> getStatistiquesEnseignantByIdAndAnnee(@PathVariable Long enseignantId, @RequestParam String anneeUniversitaire) {
        List<Classe> enseignantClasses = soumissionRepository.getClassesByEnseignant(enseignantId, anneeUniversitaire);
        List<Evaluation> evaluations = new ArrayList<>();
        enseignantClasses.forEach(classe -> {
            List<Evaluation> retievedEvaluations = soumissionRepository.getEvaluationsByClasse(classe.getId());
            if (!retievedEvaluations.isEmpty()) {
                evaluations.addAll(retievedEvaluations);
            }
        });

        List<Soumission> soumissionList = new ArrayList<>();

        evaluations.forEach(evaluation -> {
            List<Soumission> evalSoumissions = soumissionRepository.getSoumissionByEvaluation(evaluation.getId());
            if (evalSoumissions.size() > 0) {
                soumissionList.addAll(evalSoumissions);
            }
        });

        List<Formulaire> formulaires = new ArrayList<>();
        soumissionList.forEach(soumission -> {
            formulaires.add(soumission.getFormulaire());
        });

        Map<Long, ClasseIndexStatisticsDTO> classeIndexStatisticsDTOMap = new HashMap<>();

        for (Formulaire f : formulaires) {
            for (Section s : f.getSections()) {
                if (s.getEnseignantId() == null || !s.getEnseignantId().equals(enseignantId)) {
                    continue;
                }
                for (Question q : s.getQuestions()) {
                    for (Critere c : q.getCriteres()) {
                        Utilisateur enseignant = userRepository.findById(s.getEnseignantId()).get();
                        String enseignantName = enseignant.getLastname() + " " + enseignant.getFirstName();
                        classeIndexStatisticsDTOMap = addToStatsEnseignant(s.getClasseId(), s.getClasseName(), s.getSectionId(), s.getSectionName(), q.getQuestionIndex(), q.getQuestionText(), c.getCritereIndex(), c.getTitre(), c.getReponse(), classeIndexStatisticsDTOMap, enseignantName);
                    }
                }
            }
        }


        return classeIndexStatisticsDTOMap;
    }

    @GetMapping("/getMoyennesEnseignantByIdAndAnnee/{enseignantId}")
    public List<MoyenneFormation>  getMoyennesEnseignantByIdAndAnnee(@PathVariable Long enseignantId, @RequestParam String anneeUniversitaire) {
        List<Classe> enseignantClasses = soumissionRepository.getClassesByEnseignant(enseignantId, anneeUniversitaire);
        List<Evaluation> evaluations = new ArrayList<>();
        enseignantClasses.forEach(classe -> {
            List<Evaluation> retievedEvaluations = soumissionRepository.getEvaluationsByClasse(classe.getId());
            if (!retievedEvaluations.isEmpty()) {
                evaluations.addAll(retievedEvaluations);
            }
        });

        List<Soumission> soumissionList = new ArrayList<>();

        evaluations.forEach(evaluation -> {
            List<Soumission> evalSoumissions = soumissionRepository.getSoumissionByEvaluation(evaluation.getId());
            if (evalSoumissions.size() > 0) {
                soumissionList.addAll(evalSoumissions);
            }
        });

        List<MoyenneFormation> formations = calculateMoyenneEnseignant(soumissionList, enseignantId);

        return formations;
    }

    @GetMapping("/getSoumissionByDate")
    public List<Soumission> getSoumissionByDate() {
        return this.soumissionRepository.getSoumissionByDate();
    }

    public List<MoyenneFormation> calculateMoyenneEnseignant(List<Soumission> soumissionList, Long enseignantId) {
        List<MoyenneFormation> moyenneFormationList = new ArrayList<>();

        Map<Long, ClasseIndexStatisticsDTO> classeIndexStatisticsDTOMap = new HashMap<>();

        for (Soumission soumission : soumissionList) {
            for (Section section : soumission.getFormulaire().getSections()) {
                if(section.getEnseignantId() == null || !section.getEnseignantId().equals(enseignantId)){
                    continue;
                }
                for (Question question : section.getQuestions()) {
                    for (Critere critere : question.getCriteres()) {
                        Utilisateur enseignant = userRepository.findById(section.getEnseignantId()).get();
                        String enseignantName = enseignant.getLastname() + " " + enseignant.getFirstName();
                        classeIndexStatisticsDTOMap = addToStatsEnseignant(section.getClasseId(), section.getClasseName(), section.getSectionId(), section.getSectionName(), question.getQuestionIndex(), question.getQuestionText(), critere.getCritereIndex(), critere.getTitre(), critere.getReponse(), classeIndexStatisticsDTOMap, enseignantName);
                    }
                }
            }
        }

        for (Map.Entry<Long, ClasseIndexStatisticsDTO> classe : classeIndexStatisticsDTOMap.entrySet()) {

            MoyenneFormation moyenneFormation = new MoyenneFormation();
            moyenneFormation.setName(classe.getValue().getClasseName());

            for (Map.Entry<String, SectionStatisticDTO> section : classe.getValue().getSections().entrySet()) {
                MoyenneCours moyenneCours = new MoyenneCours();
                moyenneCours.setEnseignant(section.getValue().getEnseignantName());
                moyenneCours.setCourseName(section.getValue().getSectionName());

                double totalQuestions = 0;


                for (Map.Entry<String, QuestionStatisticDTO> question : section.getValue().getQuestions().entrySet()) {

                    double totalCriteres = 0;
                    for (Map.Entry<String, CritereStatisticDTO> critere : question.getValue().getCriteres().entrySet()) {
                        double totalReponses = 0;

                        Integer nbReponse = 0;
                        for (Map.Entry<String, Integer> reponse : critere.getValue().getResponses().entrySet()) {

                            nbReponse += reponse.getValue();
                            double valeurReponse = 0;
                            switch (reponse.getKey()) {
                                case "Oui":
                                    valeurReponse = (double) reponse.getValue() * 20;
                                    break;
                                case "Plutot Oui":
                                    valeurReponse = (double) reponse.getValue() * 12;
                                    break;
                                case "Plutot Non":
                                    valeurReponse = (double) reponse.getValue() * 8;
                                    break;
                                case "Non":
                                    valeurReponse = (double) reponse.getValue() * 0;
                                    break;
                                default:
                                    break;
                            }
                            totalReponses += valeurReponse;
                        }
                        double moyenneCritere = totalReponses / nbReponse;
                        totalCriteres += moyenneCritere;

                    }
                    double moyenneQuestions = totalCriteres / question.getValue().getCriteres().size();
                    totalQuestions += moyenneQuestions;
                }

                double moyenneSection = totalQuestions / section.getValue().getQuestions().size();
                moyenneCours.setScore(Double.valueOf(df.format(moyenneSection)));


                moyenneFormation.getMoyennesCours().add(moyenneCours);
            }

            moyenneFormationList.add(moyenneFormation);
        }

        return moyenneFormationList;
    }

    public MoyenneEval calculateMoyenne(List<Soumission> soumissionList) {
        MoyenneEval moyenneEval = new MoyenneEval();

        Map<String, SectionStatisticDTO> sectionStatisticDTOS = new HashMap<>();

        for (Soumission soumission : soumissionList) {
            for (Section section : soumission.getFormulaire().getSections()) {
                for (Question question : section.getQuestions()) {
                    for (Critere critere : question.getCriteres()) {
                        Utilisateur enseignant = userRepository.findById(section.getEnseignantId()).get();
                        String enseignantName = enseignant.getLastname() + " " + enseignant.getFirstName();
                        sectionStatisticDTOS = addToStats(section.getSectionId(), section.getSectionName(), question.getQuestionIndex(), question.getQuestionText(), critere.getCritereIndex(), critere.getTitre(), critere.getReponse(), sectionStatisticDTOS, enseignantName);
                    }
                }
            }
        }

        MoyenneFormation moyenneFormation = new MoyenneFormation();
        MoyenneBarometre moyenneBarometre = new MoyenneBarometre();

        double totalSections = 0;

        List<MoyenneCours> moyenneCoursList = new ArrayList<>();

        for (Map.Entry<String, SectionStatisticDTO> section : sectionStatisticDTOS.entrySet()) {
            if (!section.getValue().getSectionName().equals("Baromètre de satisfaction")) {

                MoyenneCours moyenneCours = new MoyenneCours();
                moyenneCours.setEnseignant(section.getValue().getEnseignantName());
                moyenneCours.setCourseName(section.getValue().getSectionName());

                double totalQuestions = 0;


                for (Map.Entry<String, QuestionStatisticDTO> question : section.getValue().getQuestions().entrySet()) {

                    double totalCriteres = 0;
                    for (Map.Entry<String, CritereStatisticDTO> critere : question.getValue().getCriteres().entrySet()) {
                        double totalReponses = 0;

                        for (Map.Entry<String, Integer> reponse : critere.getValue().getResponses().entrySet()) {

                            double valeurReponse = 0;
                            switch (reponse.getKey()) {
                                case "Oui":
                                    valeurReponse = (double) reponse.getValue() * 20;
                                    break;
                                case "Plutot Oui":
                                    valeurReponse = (double) reponse.getValue() * 12;
                                    break;
                                case "Plutot Non":
                                    valeurReponse = (double) reponse.getValue() * 8;
                                    break;
                                case "Non":
                                    valeurReponse = (double) reponse.getValue() * 0;
                                    break;
                                default:
                                    break;
                            }
                            totalReponses += valeurReponse;
                        }
                        double moyenneCritere = totalReponses / soumissionList.size();
                        totalCriteres += moyenneCritere;

                    }
                    double moyenneQuestions = totalCriteres / question.getValue().getCriteres().size();
                    totalQuestions += moyenneQuestions;
                }

                double moyenneSection = totalQuestions / section.getValue().getQuestions().size();

                moyenneCours.setScore(Double.valueOf(df.format(moyenneSection)));

                totalSections += moyenneSection;
                moyenneCoursList.add(moyenneCours);
            } else if (section.getValue().getSectionName().equals("Baromètre de satisfaction")) {

                moyenneBarometre.setNom("Baromètre");
                Map<String, Double> scores = new HashMap<>();

                double totalQuestions = 0;


                for (Map.Entry<String, QuestionStatisticDTO> question : section.getValue().getQuestions().entrySet()) {

                    double totalCriteres = 0;
                    for (Map.Entry<String, CritereStatisticDTO> critere : question.getValue().getCriteres().entrySet()) {
                        double totalReponses = 0;

                        for (Map.Entry<String, Integer> reponse : critere.getValue().getResponses().entrySet()) {

                            double valeurReponse = 0;
                            switch (reponse.getKey()) {
                                case "Oui":
                                    valeurReponse = (double) reponse.getValue() * 20;
                                    break;
                                case "Plutot Oui":
                                    valeurReponse = (double) reponse.getValue() * 12;
                                    break;
                                case "Plutot Non":
                                    valeurReponse = (double) reponse.getValue() * 8;
                                    break;
                                case "Non":
                                    valeurReponse = (double) reponse.getValue() * 0;
                                    break;
                                default:
                                    break;
                            }
                            totalReponses += valeurReponse;
                        }
                        double moyenneCritere = totalReponses / soumissionList.size();
                        totalCriteres += moyenneCritere;


                    }
                    double moyenneQuestions = totalCriteres / question.getValue().getCriteres().size();
                    totalQuestions += moyenneQuestions;

                    scores.put(question.getValue().getQuestionText(), Double.valueOf(df.format(moyenneQuestions)));
                }

                double moyenneSection = totalQuestions / section.getValue().getQuestions().size();
                moyenneBarometre.setMoyenne(Double.valueOf(df.format(moyenneSection)));
                moyenneBarometre.setScores(scores);
            }
        }

        long numberOfMatieres = sectionStatisticDTOS.entrySet().stream()
                .filter(entry -> !entry.getValue().getSectionName().equals("Baromètre de satisfaction"))
                .count();


        double moyenneSections = totalSections / numberOfMatieres;
        moyenneFormation.setIndiceRetour(Double.valueOf(df.format(moyenneSections)));
        moyenneFormation.setMoyennesCours(moyenneCoursList);
        moyenneFormation.setName("Retour d’expérience: formation");

        moyenneEval.setMoyenneBarometre(moyenneBarometre);
        moyenneEval.setMoyenneFormation(moyenneFormation);

        return moyenneEval;
    }

    public Map<String, SectionStatisticDTO> addToStats(String sectionIndex, String sectionName, String questionIndex, String questionName, String critereIndex, String critereName, String critereResponse, Map<String, SectionStatisticDTO> sectionStatisticDTOS, String enseignantName) {
        SectionStatisticDTO sectionStatisticDTO = sectionStatisticDTOS.get(sectionIndex);

        if (sectionStatisticDTO != null) {
            QuestionStatisticDTO questionStatisticDTO = sectionStatisticDTO.getQuestions().get(questionIndex);

            if (questionStatisticDTO != null) {

                CritereStatisticDTO critereStatisticDTO = questionStatisticDTO.getCriteres().get(critereIndex);

                if (critereStatisticDTO != null) {

                    if (critereStatisticDTO.getResponses().containsKey(critereResponse)) {
                        int count = critereStatisticDTO.getResponses().get(critereResponse);
                        critereStatisticDTO.getResponses().put(critereResponse, count + 1);
                        sectionStatisticDTOS.get(sectionIndex).getQuestions().get(questionIndex).getCriteres()
                                .get(critereIndex).setResponses(critereStatisticDTO.getResponses());

                    } else {
                        critereStatisticDTO.getResponses().put(critereResponse, 1);
                        sectionStatisticDTOS.get(sectionIndex).getQuestions().get(questionIndex).getCriteres()
                                .get(critereIndex).setResponses(critereStatisticDTO.getResponses());
                    }

                } else {
                    CritereStatisticDTO newCritereStatisticDTO = createEmptyCritere(critereIndex, critereName, critereResponse);
                    sectionStatisticDTOS.get(sectionIndex).getQuestions().get(questionIndex).getCriteres().put(critereIndex, newCritereStatisticDTO);
                }

            } else {
                QuestionStatisticDTO newQuestionStatisticDTO = createEmptyQuestion(questionIndex, questionName, critereIndex, critereName, critereResponse);
                sectionStatisticDTOS.get(sectionIndex).getQuestions().put(questionIndex, newQuestionStatisticDTO);
            }
        } else {
            SectionStatisticDTO newSectionStatisticDTO = createEmptySection(sectionIndex, sectionName, questionIndex, questionName, critereIndex, critereName, critereResponse, enseignantName);

            sectionStatisticDTOS.put(sectionIndex, newSectionStatisticDTO);
        }

        return sectionStatisticDTOS;
    }


    public Map<Long, ClasseIndexStatisticsDTO> addToStatsEnseignant(Long classeIndex, String classeName, String sectionIndex, String sectionName, String questionIndex, String questionName, String critereIndex, String critereName, String critereResponse, Map<Long, ClasseIndexStatisticsDTO> classeIndexStatisticsDTOMap, String enseignantName) {
        ClasseIndexStatisticsDTO classeIndexStatisticsDTO = classeIndexStatisticsDTOMap.get(classeIndex);

        if (classeIndexStatisticsDTO != null) {


            SectionStatisticDTO sectionStatisticDTO = classeIndexStatisticsDTO.getSections().get(sectionIndex);

            if (sectionStatisticDTO != null) {
                QuestionStatisticDTO questionStatisticDTO = sectionStatisticDTO.getQuestions().get(questionIndex);

                if (questionStatisticDTO != null) {

                    CritereStatisticDTO critereStatisticDTO = questionStatisticDTO.getCriteres().get(critereIndex);

                    if (critereStatisticDTO != null) {

                        if (critereStatisticDTO.getResponses().containsKey(critereResponse)) {
                            int count = critereStatisticDTO.getResponses().get(critereResponse);
                            critereStatisticDTO.getResponses().put(critereResponse, count + 1);
                            classeIndexStatisticsDTOMap.get(classeIndex).getSections().get(sectionIndex).getQuestions().get(questionIndex).getCriteres()
                                    .get(critereIndex).setResponses(critereStatisticDTO.getResponses());

                        } else {
                            critereStatisticDTO.getResponses().put(critereResponse, 1);
                            classeIndexStatisticsDTOMap.get(classeIndex).getSections().get(sectionIndex).getQuestions().get(questionIndex).getCriteres()
                                    .get(critereIndex).setResponses(critereStatisticDTO.getResponses());
                        }

                    } else {
                        CritereStatisticDTO newCritereStatisticDTO = createEmptyCritere(critereIndex, critereName, critereResponse);
                        classeIndexStatisticsDTOMap.get(classeIndex).getSections().get(sectionIndex).getQuestions().get(questionIndex).getCriteres().put(critereIndex, newCritereStatisticDTO);
                    }

                } else {
                    QuestionStatisticDTO newQuestionStatisticDTO = createEmptyQuestion(questionIndex, questionName, critereIndex, critereName, critereResponse);
                    classeIndexStatisticsDTOMap.get(classeIndex).getSections().get(sectionIndex).getQuestions().put(questionIndex, newQuestionStatisticDTO);
                }
            } else {
                SectionStatisticDTO newSectionStatisticDTO = createEmptySection(sectionIndex, sectionName, questionIndex, questionName, critereIndex, critereName, critereResponse, enseignantName);

                classeIndexStatisticsDTOMap.get(classeIndex).getSections().put(sectionIndex, newSectionStatisticDTO);
            }

        } else {
            ClasseIndexStatisticsDTO newClasseIndexStatisticsDTO = createEmptyClasseIndex(classeIndex, classeName, sectionIndex, sectionName, questionIndex, questionName, critereIndex, critereName, critereResponse, enseignantName);

            classeIndexStatisticsDTOMap.put(classeIndex, newClasseIndexStatisticsDTO);
        }


        return classeIndexStatisticsDTOMap;
    }

    public SectionStatisticDTO createEmptySection(String sectionIndex, String sectionName, String questionIndex, String questionText, String critereIndex, String critereName, String critereResponse, String enseignantName) {
        SectionStatisticDTO newSectionStatisticDTO = new SectionStatisticDTO();
        newSectionStatisticDTO.setSectionIndex(sectionIndex);
        newSectionStatisticDTO.setSectionName(sectionName);
        newSectionStatisticDTO.setEnseignantName(enseignantName);

        QuestionStatisticDTO newQuestionStatisticDTO = new QuestionStatisticDTO();
        newQuestionStatisticDTO.setQuestionText(questionText);
        newQuestionStatisticDTO.setQuestionIndex(questionIndex);
        newSectionStatisticDTO.getQuestions().put(questionIndex, newQuestionStatisticDTO);

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setCritereIndex(critereIndex);
        newCritereStatisticDTO.setTitle(critereName);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        newQuestionStatisticDTO.getCriteres().put(critereIndex, newCritereStatisticDTO);

        return newSectionStatisticDTO;
    }

    public QuestionStatisticDTO createEmptyQuestion(String questionIndex, String questionText, String critereIndex, String critereName, String critereResponse) {

        QuestionStatisticDTO newQuestionStatisticDTO = new QuestionStatisticDTO();
        newQuestionStatisticDTO.setQuestionIndex(questionIndex);
        newQuestionStatisticDTO.setQuestionText(questionText);

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setCritereIndex(critereIndex);
        newCritereStatisticDTO.setTitle(critereName);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        newQuestionStatisticDTO.getCriteres().put(critereIndex, newCritereStatisticDTO);

        return newQuestionStatisticDTO;
    }

    public CritereStatisticDTO createEmptyCritere(String critereIndex, String critereTitle, String critereResponse) {

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setCritereIndex(critereIndex);
        newCritereStatisticDTO.setTitle(critereTitle);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        return newCritereStatisticDTO;
    }

    public ClasseIndexStatisticsDTO createEmptyClasseIndex(Long classeIndex, String classeName, String sectionIndex, String sectionName, String questionIndex, String questionText, String critereIndex, String critereName, String critereResponse, String enseignantName) {

        ClasseIndexStatisticsDTO newClasseIndexStatisticsDTO = new ClasseIndexStatisticsDTO();
        newClasseIndexStatisticsDTO.setClasseIndex(classeIndex);
        newClasseIndexStatisticsDTO.setClasseName(classeName);

        SectionStatisticDTO newSectionStatisticDTO = new SectionStatisticDTO();
        newSectionStatisticDTO.setSectionIndex(sectionIndex);
        newSectionStatisticDTO.setSectionName(sectionName);
        newSectionStatisticDTO.setClasseName(classeName);
        newSectionStatisticDTO.setEnseignantName(enseignantName);
        newClasseIndexStatisticsDTO.getSections().put(sectionIndex, newSectionStatisticDTO);

        QuestionStatisticDTO newQuestionStatisticDTO = new QuestionStatisticDTO();
        newQuestionStatisticDTO.setQuestionText(questionText);
        newQuestionStatisticDTO.setQuestionIndex(questionIndex);
        newSectionStatisticDTO.getQuestions().put(questionIndex, newQuestionStatisticDTO);

        CritereStatisticDTO newCritereStatisticDTO = new CritereStatisticDTO();
        newCritereStatisticDTO.setCritereIndex(critereIndex);
        newCritereStatisticDTO.setTitle(critereName);
        newCritereStatisticDTO.getResponses().put(critereResponse, 1);

        newQuestionStatisticDTO.getCriteres().put(critereIndex, newCritereStatisticDTO);

        return newClasseIndexStatisticsDTO;
    }
}
