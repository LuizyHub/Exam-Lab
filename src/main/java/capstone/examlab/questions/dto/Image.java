package capstone.examlab.questions.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
