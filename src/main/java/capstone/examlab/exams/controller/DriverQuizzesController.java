package capstone.examlab.exams.controller;

import capstone.examlab.exams.entity.QuestionEntity;
import capstone.examlab.exams.repository.DriverQuizzesRepository;
import capstone.examlab.exams.service.ExamsService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("/driver-exam/")
@RestController
public class DriverQuizzesController {
    private final DriverQuizzesRepository driverQuizzesRepository;
    private final ExamsService examservice;

    public DriverQuizzesController(DriverQuizzesRepository driverQuizzesRepository, ExamsService examservice) {
        this.driverQuizzesRepository = driverQuizzesRepository;
        this.examservice = examservice;
    }

    @GetMapping("get")
    public Iterable<QuestionEntity> findAll() {
        return driverQuizzesRepository.findAll();
    }

    @GetMapping("count")
    public long countAll() {
        return driverQuizzesRepository.count();
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

        Iterable<QuestionEntity> questions = examservice.findByUserSearch(tags, count, includes);
        return questions;
    }


    @PostMapping("post")
    public ResponseEntity save(@RequestBody List<QuestionEntity> questionEntities) {
        driverQuizzesRepository.saveAll(questionEntities);
        return ResponseEntity.ok("dataAddSuccess");
    }

    @PutMapping("update")
    public QuestionEntity update(@RequestBody QuestionEntity questionEntity) throws Exception {
            return driverQuizzesRepository.save(questionEntity);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAll() {
        driverQuizzesRepository.deleteAll();
        return ResponseEntity.ok("DeleteAll");
    }
}