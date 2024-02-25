package capstone.examlab.exams.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class Question {
    private Long id;
    private String type;
    private String question;
    private ArrayList<String> questionImageUrls;
    private ArrayList<String> questionImageDescriptions;
    private ArrayList<String> options;
    private ArrayList<Integer> answers;
    private String commentary;
    private ArrayList<String> commentaryImageUrls;
    private ArrayList<String> commentaryImageDescriptions;
    private ArrayList<String> tags;
}
