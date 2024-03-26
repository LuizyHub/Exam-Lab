package capstone.examlab.questions.service;

import capstone.examlab.questions.dto.ImagesSaveDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    String saveImageInS3(MultipartFile multipartImage, int index);

    List<String> saveTestImagesInS3(ImagesSaveDto imagesSaveDto);

    void deleteImagesInFolder();
}
