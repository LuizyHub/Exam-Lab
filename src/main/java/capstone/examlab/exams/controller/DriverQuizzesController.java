package capstone.examlab.exams.controller;

import capstone.examlab.exams.entity.Question;
import capstone.examlab.exams.repository.DriverQuizzesRepository;
import capstone.examlab.exams.service.ExamsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Question> findAll() {
        return driverQuizzesRepository.findAll();
    }

    @GetMapping("count")
    public long countAll() {
        return driverQuizzesRepository.count();
    }


    @GetMapping("123/questions")
    public Iterable<Question> findByUserSearch(
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "count", defaultValue = "20") int count,
            @RequestParam(value = "includes", required = false) String includes
    ) {
        Iterable<Question> quizzes = examservice.findByUserSearch(tags, count, includes);
        return quizzes;
    }


    @PostMapping("post")
    public ResponseEntity save(@RequestBody List<Question> questions) {
        driverQuizzesRepository.saveAll(questions);
        return ResponseEntity.ok("dataAddSuccess");
    }

    @PutMapping("update")
    public Question update(@RequestBody Question question) throws Exception {
            return driverQuizzesRepository.save(question);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        driverQuizzesRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAll() {
        driverQuizzesRepository.deleteAll();
        return ResponseEntity.ok("DeleteAll");
    }
}