package capstone.examlab.questions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsOption {
    private List<String> tags;
    @Builder.Default
    private Integer count = 10;
    private List<String> includes;

    public void setTags(Map<String, List<String>> tags) {
        this.tags = tags.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> entry.getKey() + "-" + value))
                .collect(Collectors.toList());
    }
}