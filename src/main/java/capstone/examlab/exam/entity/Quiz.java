package capstone.examlab.exam.entity;
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
    //json에는 id가 string형임(변환할때 string으로 만듬)
    //결론: 추후 그 코드 수정하고 다시 json만들기 또는 json id받아올때 int로 변환해주던지
    //Text vs Keyword:
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