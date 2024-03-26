package capstone.examlab.questions.service;

import capstone.examlab.questions.dto.ImagesSaveDto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private static String BUCKET_NAME = "examlab-image";
    private static String FOLDER_NAME = "test_images/";  //저장 폴더명 임시 부여
    //private static String FOLDER_NAME = "driver_images/"; //실제 사용 폴더명
    private final AmazonS3 amazonS3;

    @Transactional
    public String saveImageInS3(MultipartFile multipartImage, int index){
        String originalName = multipartImage.getOriginalFilename();
        String accessUrl = ""; //반환 URL저장
        String filename = FOLDER_NAME+index+originalName;
        log.info(filename);
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartImage.getContentType());
            objectMetadata.setContentLength(multipartImage.getInputStream().available());

            amazonS3.putObject(BUCKET_NAME, filename, multipartImage.getInputStream(), objectMetadata);

            accessUrl = amazonS3.getUrl(BUCKET_NAME, filename).toString();
        } catch(IOException e) {

        }
        return accessUrl;
    }

    //이미지 저장 로직
    @Transactional
    public List<String> saveTestImagesInS3(ImagesSaveDto imagesSaveDto) {
        List<String> resultList = new ArrayList<>();

        for (int i = 0; i < imagesSaveDto.getImages().size(); i++) {
            String value = saveTestImage(imagesSaveDto.getImages().get(i));
            resultList.add(value);
        }
        return resultList;
    }

    @Transactional
    public String saveTestImage(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        String accessUrl = ""; //반환 URL저장
        String filename = FOLDER_NAME+originalName;

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3.putObject(BUCKET_NAME, filename, multipartFile.getInputStream(), objectMetadata);

            accessUrl = amazonS3.getUrl(BUCKET_NAME, filename).toString();
        } catch(IOException ignored) {

        }
        return accessUrl;
    }

    //이미지 삭제 로직
    public void deleteImagesInFolder() {
        // 폴더 내 객체 리스트를 가져옴
        List<S3ObjectSummary> objectSummaries = amazonS3.listObjects(BUCKET_NAME, FOLDER_NAME).getObjectSummaries();

        // 각 객체를 삭제
        for (S3ObjectSummary objectSummary : objectSummaries) {
            amazonS3.deleteObject(new DeleteObjectRequest(BUCKET_NAME, objectSummary.getKey()));
        }
    }
}
