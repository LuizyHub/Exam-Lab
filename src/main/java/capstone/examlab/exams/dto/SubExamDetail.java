package capstone.examlab.exams.dto;

import lombok.Data;

@Data
public class SubExamDetail {
    private Long examId;
    private String subTitle;

    public SubExamDetail(){}
}