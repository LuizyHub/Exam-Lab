package capstone.examlab.questions.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class Image {
    private String url;
    private String description;
    private String attribute;
    public Image(String url, String description, String attribute) {
        this.url = url;
        this.description = description;
        this.attribute = attribute;
    }

}
