package capstone.examlab.questions.entity;
import capstone.examlab.questions.dto.Image;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
@Document(indexName = "questions")
public class QuestionEntity {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private Long examId;
    @Field(type = FieldType.Keyword)
    private String type;
    @Field(type = FieldType.Text)
    private String question;
    @Field(type = FieldType.Text)
    private List<String> options;
    @Field(type = FieldType.Object)
    private List<Image> questionImagesIn;
    @Field(type = FieldType.Object)
    private List<Image> questionImagesOut;
    @Field(type = FieldType.Keyword)
    private List<Integer> answers;
    @Field(type = FieldType.Text)
    private String commentary;
    @Field(type = FieldType.Object)
    private List<Image> commentaryImagesIn;
    @Field(type = FieldType.Object)
    private List<Image> commentaryImagesOut;
    @Field(type = FieldType.Object)
    private Map<String, List<String>> tagsMap;
}