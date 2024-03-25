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
    private  Map<String, List<String>> tagsMap;
    @Builder.Default
    private Integer count = 10;
    private List<String> includes;
}