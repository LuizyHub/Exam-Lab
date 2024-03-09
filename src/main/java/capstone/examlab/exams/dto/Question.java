package capstone.examlab.exams.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Question {
    private Long id;
    private String type;
    private String question;
    private ArrayList<Image> questionImagesIn;
    private ArrayList<Image> questionImagesOut;
    private ArrayList<String> options;
    private ArrayList<String> answers;
    private String commentary;
    private ArrayList<Image> commentaryImagesIn;
    private ArrayList<Image> commentaryImagesOut;
    private ArrayList<String> tags;
}