package capstone.examlab.exam;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Quiz {
    //json에는 id가 string형임(변환할때 string으로 만듬...
    //결론: 추후 그 코드 수정하고 다시 json만들기 또는 json id받아올때 int로 변환해주던지
    private int id;
    private String type;
    private String question;
    private List<String> options;
    private List<String> questionImageUrls;
    private List<String> questionImageDescriptions;
    private List<String> answers;
    private String explanation;
    private List<String> explanationImageUrls;
    private List<String> explanationImageDescriptions;
    private List<String> tags;
    // 롬복이 아닌 기본 생성자 추가
    public Quiz() {
    }

    // 롬복이 자동 생성하지 않는 생성자 추가
    public Quiz(int id, String type, String question, List<String> options, List<String> questionImageUrls, List<String> questionImageDescriptions, List<String> answers, String explanation, List<String> explanationImageUrls, List<String> explanationImageDescriptions, List<String> tags) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.options = options;
        this.questionImageUrls = questionImageUrls;
        this.questionImageDescriptions = questionImageDescriptions;
        this.answers = answers;
        this.explanation = explanation;
        this.explanationImageUrls = explanationImageUrls;
        this.explanationImageDescriptions = explanationImageDescriptions;
        this.tags = tags;
    }

}