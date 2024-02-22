package capstone.examlab.exams.controller;


import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.service.ExamsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/exams")
public class ExamsController {

    private final ExamsService examsService;

    @GetMapping("get")
    public ExamList getExams() {
        ExamList examList = examsService.getExamList();
        return examList;
    }
}
