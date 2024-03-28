package capstone.examlab.questions.dto;

import lombok.Data;
import lombok.Builder;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class QuestionUpdateDto {
    private String id;
    private String question;
    private List<String> options;
    private List<Integer> answers;
    private String commentary;
    private Map<String, List<String>> tagsMap;
}