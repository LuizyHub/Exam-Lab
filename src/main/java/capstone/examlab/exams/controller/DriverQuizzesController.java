package capstone.examlab.exams.controller;

import capstone.examlab.exams.entity.Quiz;
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
    public Iterable<Quiz> findAll() {
        return driverQuizzesRepository.findAll();
    }

    @GetMapping("count")
    public long countAll() {
        return driverQuizzesRepository.count();
    }


    @GetMapping("123/questions")
    public Iterable<Quiz> findByUserSearch(
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "count", defaultValue = "20") int count,
            @RequestParam(value = "includes", required = false) String includes
    ) {
        Iterable<Quiz> quizzes = examservice.findByUserSearch(tags, count, includes);
        return quizzes;
    }


    @PostMapping("post")
    public ResponseEntity save(@RequestBody List<Quiz> quizzes) {
        driverQuizzesRepository.saveAll(quizzes);
        return ResponseEntity.ok("dataAddSuccess");
    }

    @PutMapping("update")
    public Quiz update(@RequestBody Quiz quiz) throws Exception {
        if (quiz.getId() != 0) {
            return driverQuizzesRepository.save(quiz);
        }
        throw new Exception("Id is required");
    }

/*    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        driverQuizzesRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAll() {
        driverQuizzesRepository.deleteAll();
        return ResponseEntity.ok("DeleteAll");
    }*/
}