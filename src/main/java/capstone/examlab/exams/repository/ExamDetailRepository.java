package capstone.examlab.exams.repository;

import capstone.examlab.exams.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamDetailRepository extends JpaRepository<Exam, Integer> {
    
}
