package capstone.examlab.questions.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ImagesSaveDto {
    private List<MultipartFile> images;
    private List<String> str;
}
