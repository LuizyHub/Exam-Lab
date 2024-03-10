package capstone.examlab.questions.controller;

import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.repository.DriverLicenseQuestionsRepository;
import capstone.examlab.exams.service.ExamsService;
import capstone.examlab.questions.service.QuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequestMapping("/driver-exam/")
@RestController
@RequiredArgsConstructor
public class QuestionsController {
    private final DriverLicenseQuestionsRepository driverLicenseQuestionsRepository;
    private final QuestionsService questionsService;

    @GetMapping("get")
    public Iterable<QuestionEntity> findAll() {
        return driverLicenseQuestionsRepository.findAll();
    }

    @GetMapping("count")
    public long countAll() {
        return driverLicenseQuestionsRepository.count();
    }

    @GetMapping("123/questions")
    public Iterable<QuestionEntity> findByUserSearch(
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "count", defaultValue = "20") int count,
            @RequestParam(value = "includes", required = false) String includes
    ) {
        if (tags == null) {
            tags = Collections.emptyList();
        }

        if (includes == null){
            includes = "";
        }

        Iterable<QuestionEntity> questions = questionsService.findByDriverLicenseQuestions(tags, count, includes);
        return questions;
    }


    @PostMapping("post")
    public ResponseEntity save(@RequestBody List<QuestionEntity> questionEntities) {
        driverLicenseQuestionsRepository.saveAll(questionEntities);
        return ResponseEntity.ok("dataAddSuccess");
    }

    @PutMapping("update")
    public QuestionEntity update(@RequestBody QuestionEntity questionEntity) throws Exception {
            return driverLicenseQuestionsRepository.save(questionEntity);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAll() {
        driverLicenseQuestionsRepository.deleteAll();
        return ResponseEntity.ok("DeleteAll");
    }
}