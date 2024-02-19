package capstone.examlab.exam.controller;

import capstone.examlab.exam.entity.Quiz;
import capstone.examlab.exam.repository.DriverQuizzesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/driver-exam/")
@RestController
public class DriverQuizzesController {
    private final DriverQuizzesRepository driverQuizzesRepository;

    @Autowired
    public DriverQuizzesController(DriverQuizzesRepository driverQuizzesRepository) {
        this.driverQuizzesRepository = driverQuizzesRepository;
    }

    @GetMapping("get")
    public Iterable<Quiz> findAll() {
        return driverQuizzesRepository.findAll();
    }

    @GetMapping("count")
    public long countAll() {
        return driverQuizzesRepository.count();
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