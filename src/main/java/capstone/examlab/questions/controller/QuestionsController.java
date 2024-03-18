package capstone.examlab.questions.controller;

import capstone.examlab.questions.dto.ImageSaveDto;
import capstone.examlab.questions.dto.QuestionsList;
import capstone.examlab.questions.dto.QuestionsOption;
import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.repository.DriverLicenseQuestionsRepository;
import capstone.examlab.questions.service.QuestionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequestMapping("api/v1/test/exams/")
@RestController
@RequiredArgsConstructor
public class QuestionsController {
    private final DriverLicenseQuestionsRepository driverLicenseQuestionsRepository;
    private final QuestionsService questionsService;

    //데이터 조회 API
    @GetMapping("{examId}/questions")
    public QuestionsList getExamQuestions(@PathVariable Long examId, @ModelAttribute QuestionsOption questionsOption) {
        log.info("questionOptionDto = {}", questionsOption);
        return questionsService.findByDriverLicenseQuestions(examId, questionsOption);
    }

    //데이터 테스트용 API
    @GetMapping("get")
    public QuestionsList findAll() {
        return questionsService.findAllByDriverLicneseQuestions();
    }

    @GetMapping("count")
    public long countAll() {
        return questionsService.countAllByDriverLicenseQuestions();
    }

    @PostMapping("post")
    public ResponseEntity save(@RequestBody List<QuestionEntity> questionEntities) {
        questionsService.saveDriverLicenseQuestions(questionEntities);
        return ResponseEntity.ok("dataAddSuccess");
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAll() {
        questionsService.deleteDriverLicenseQuestions();
        return ResponseEntity.ok("DeleteAll");
    }

    //이미지 관련 API
    @PostMapping("image")
    public List<String> saveImages(@ModelAttribute ImageSaveDto imageSaveDto) {
        return questionsService.saveImages(imageSaveDto);
    }

    @DeleteMapping("image/delete")
    public ResponseEntity<String> deleteImages(){
        questionsService.deleteImages();
        return ResponseEntity.ok("DeleteAll");
    }
}
