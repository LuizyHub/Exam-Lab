package capstone.examlab.questions.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Question {
    private String id;
    private String type;
    private String question;
    private List<Image> questionImagesIn;
    private List<Image> questionImagesOut;
    private List<String> options;
    private List<Integer> answers;
    private String commentary;
    private List<Image> commentaryImagesIn;
    private List<Image> commentaryImagesOut;
    private Map<String, List<String>> tagsMap;
}