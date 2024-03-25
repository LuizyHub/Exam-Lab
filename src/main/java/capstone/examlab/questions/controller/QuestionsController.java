package capstone.examlab.questions.controller;

import capstone.examlab.questions.dto.ImageSaveDto;
import capstone.examlab.questions.dto.QuestionsList;
import capstone.examlab.questions.dto.QuestionsOption;
import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.service.QuestionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("api/v1/test/exams/")
@RestController
@RequiredArgsConstructor
public class QuestionsController {
    private final QuestionsService questionsService;

    //데이터 조회 API
    @GetMapping("{examId}/questions/search")
    public QuestionsList getExamQuestions(@PathVariable Long examId, @RequestBody QuestionsOption questionsOption) {
        log.info("questionOptionDto = {}", questionsOption);
        return questionsService.searchFromQuestions(examId, questionsOption);
    }

    //데이터 테스트용 API
    @GetMapping("count")
    public long countAll() {
        return questionsService.countAllQuestions();
    }

    @PostMapping("{examId}/questions/save")
    public ResponseEntity<String> save(@PathVariable Long examId, @RequestBody List<QuestionEntity> questionEntities) {
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntity.setExamId(examId);
        }
        questionsService.saveQuestions(questionEntities);
        return ResponseEntity.ok("dataAddSuccess");
    }

    @DeleteMapping("questions")
    public ResponseEntity<String> deleteAll() {
        questionsService.deleteAllQuestions();
        return ResponseEntity.ok("DeleteAll");
    }

    //이미지 관련 API
    @PostMapping("image")
    public List<String> saveImages(@ModelAttribute ImageSaveDto imageSaveDto) {
        return questionsService.saveImages(imageSaveDto);
    }

    @DeleteMapping("image")
    public ResponseEntity<String> deleteImages(){
        questionsService.deleteImages();
        return ResponseEntity.ok("DeleteAll");
    }
}
