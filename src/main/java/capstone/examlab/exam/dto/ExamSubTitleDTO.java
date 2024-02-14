package capstone.examlab.exam.dto;

import lombok.Builder;
import lombok.Getter;

// api/v1/exams 시험지 리스트에서 sub_title내의 항목을 DTO로 표시

@Getter
@Builder
public class ExamSubTitleDTO {
    private int id;
    private String subTitle;
}
