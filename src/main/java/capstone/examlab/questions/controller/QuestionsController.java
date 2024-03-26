package capstone.examlab.questions.controller;

import capstone.examlab.questions.dto.*;
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
    //Create API, '신규 문제' 저장 API
    //Json형태의 텍스트 데이터 받기
    @PostMapping("{examId}/questions")
    public ResponseEntity<String> addQuestionsByExamId(@PathVariable Long examId, @RequestBody QuestionsUploadList questionsUploadList) {
        questionsService.addQuestionsByExamId(examId, questionsUploadList);
        return ResponseEntity.ok("data add success");
    }

    //form-data형식으로 imageFile 및 데이터 받기
    @PostMapping("questions/image")
    public ResponseEntity<String> addImagesInQuestions(@ModelAttribute ImagesUploadInfo imagesUploadInfo) {
        boolean notNull = questionsService.addImageInQuestions(imagesUploadInfo);
        if(notNull) {
            return ResponseEntity.ok("image add success");
        }
        else{
            return ResponseEntity.badRequest().body("image add error");
        }
    }

    //Read API
    @GetMapping("{examId}/questions/search")
    public QuestionsList selectQuestions(@PathVariable Long examId, @RequestBody QuestionsSearchDto questionsSearchDto) {
        log.info("questionOptionDto = {}", questionsSearchDto);
        return questionsService.searchFromQuestions(examId, questionsSearchDto);
    }

    //Delete API
    //문제들(examId) 삭제 API
    @DeleteMapping("{examId}/questions")
    public ResponseEntity<String> deleteQuestionsByExamId(@PathVariable Long examId) {
        boolean deleted = questionsService.deleteQuestionsByExamId(examId);
        if (deleted) {
            return ResponseEntity.ok("data delete success");
        } else {
            return ResponseEntity.badRequest().body("data delete error");
        }
    }

    //문제들(List<uuid>) API
    @DeleteMapping("questions/uuid")
    public ResponseEntity<String> deleteQuestionsByUUID(@RequestBody List<String> uuidList) {
        boolean deleted = questionsService.deleteQuestionsByUuidList(uuidList);
        if (deleted) {
            return ResponseEntity.ok("data delete success");
        } else {
            return ResponseEntity.badRequest().body("data delete error");
        }
    }

    //'기존 문제' 저장 로직
    @PostMapping("{examId}/questions/save")
    public ResponseEntity<String> addOriginalQuestions(@PathVariable Long examId, @RequestBody List<QuestionEntity> questionEntities) {
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntity.setExamId(examId);
        }
        questionsService.saveQuestions(questionEntities);
        return ResponseEntity.ok("data add success");
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
    public List<String> addImagesTest(@ModelAttribute ImagesSaveDto imagesSaveDto) {
        for (String s : imagesSaveDto.getStr()) {
            log.info("str: "+s);
        }
        return questionsService.saveImages(imagesSaveDto);
    }

    @DeleteMapping("image")
    public ResponseEntity<String> deleteImages(){
        questionsService.deleteAllImages();
        return ResponseEntity.ok("DeleteAll");
    }
}
