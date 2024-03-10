package capstone.examlab.questions.dto;

import capstone.examlab.questions.dto.Image;
import co.elastic.clients.elasticsearch.cat.MlDataFrameAnalyticsRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> tags;
}