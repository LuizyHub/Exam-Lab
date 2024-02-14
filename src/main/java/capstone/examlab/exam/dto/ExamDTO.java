package capstone.examlab.exam.dto;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

// api/v1/exams 에 사용될 시험지 리스트 DTO

@Getter
@Builder
public class ExamDTO {
    private String title;
    private List<ExamSubTitleDTO> subTitles;
}
