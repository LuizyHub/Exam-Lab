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

    //Read 로직
    @GetMapping("{examId}/questions/search")
    public QuestionsList selectQuestions(@PathVariable Long examId, @RequestBody QuestionsOption questionsOption) {
        log.info("questionOptionDto = {}", questionsOption);
        return questionsService.searchFromQuestions(examId, questionsOption);
    }

    //문제지(examId) 삭제 로직
    @DeleteMapping("{examId}/questions")
    public ResponseEntity<String> deleteQuestionsByExamId(@PathVariable Long examId) {
        boolean deleted = questionsService.deleteQuestionsByExamId(examId);
        if (deleted) {
            return ResponseEntity.ok("questions delete success");
        } else {
            return ResponseEntity.badRequest().body("delete error");
        }
    }

    //문제들(List<uuid>) 삭제 로직
    @DeleteMapping("questions/uuid")
    public ResponseEntity<String> deleteQuestionsByUUID(@RequestBody List<String> uuidList) {
        boolean deleted = questionsService.deleteQuestionsByUuidList(uuidList);
        if (deleted) {
            return ResponseEntity.ok("questions delete success");
        } else {
            return ResponseEntity.badRequest().body("delete error");
        }
    }

    //'기존 문제' 저장 로직
    @PostMapping("{examId}/questions/save")
    public ResponseEntity<String> save(@PathVariable Long examId, @RequestBody List<QuestionEntity> questionEntities) {
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntity.setExamId(examId);
        }
        questionsService.saveQuestions(questionEntities);
        return ResponseEntity.ok("dataAddSuccess");
    }

    //데이터 테스트용 API
    @GetMapping("questions/count")
    public long countAll() {
        return questionsService.countAllQuestions();
    }

    @DeleteMapping("questions/all")
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
        questionsService.deleteAllImages();
        return ResponseEntity.ok("DeleteAll");
    }
}
