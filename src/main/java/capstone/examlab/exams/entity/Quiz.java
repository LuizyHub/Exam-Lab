package capstone.examlab.exams.entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
@NoArgsConstructor
@Data
@Document(indexName = "driver-quiz")
public class Quiz {
    //id 변경 필요 uuid가 되어야함
    //추가로 몇몇명 변경해야함
    @Id
    private int id;
    @Field(type = FieldType.Keyword)
    private String type;
    @Field(type = FieldType.Text)
    private String question;
    @Field(type = FieldType.Text)
    private List<String> options;
    @Field(type = FieldType.Text)
    private List<String> questionImageUrls;
    @Field(type = FieldType.Text)
    private List<String> questionImageDescriptions;
    @Field(type = FieldType.Keyword)
    private List<Integer> answers;
    @Field(type = FieldType.Text)
    private String explanation;
    @Field(type = FieldType.Text)
    private List<String> explanationImageUrls;
    @Field(type = FieldType.Text)
    private List<String> explanationImageDescriptions;
    @Field(type = FieldType.Text)
    private List<String> tags;
}