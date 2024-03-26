package capstone.examlab.questions.service;

import capstone.examlab.questions.dto.*;
import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.repository.BoolQueryBuilder;
import capstone.examlab.questions.repository.QuestionsRepository;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {
    private final QuestionsRepository questionsRepository;
    private final ImageService imageService;
    private final BoolQueryBuilder boolQueryBuilder;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void addQuestionsByExamId(Long examId, QuestionsUploadList questionsUploadList) {
        List<QuestionEntity> questionEntities = new ArrayList<>();
        if(questionsUploadList != null) {
            for (QuestionUpload question  : questionsUploadList) {
                QuestionEntity questionEntity = QuestionEntity.builder()
                        .id(question.getUuid())
                        .examId(examId)
                        .type(question.getType())
                        .question(question.getQuestion())
                        .options(question.getOptions())
                        .questionImagesIn(new ArrayList<>())
                        .questionImagesOut(new ArrayList<>())
                        .answers(question.getAnswers())
                        .commentary(question.getCommentary())
                        .commentaryImagesIn(new ArrayList<>())
                        .commentaryImagesOut(new ArrayList<>())
                        .tagsMap(question.getTagsMap())
                        .build();

                questionEntities.add(questionEntity);
            }
            questionsRepository.saveAll(questionEntities);
        }
    }

    @Override
    public boolean addImageInQuestions(ImagesUploadInfo imagesUploadInfo) {
        int size = imagesUploadInfo.getImageFiles().size();
        int uuidSize = imagesUploadInfo.getQuestionsUuid().size();
        int descriptionSize = imagesUploadInfo.getDescriptions().size();
        int attributesize = imagesUploadInfo.getDescriptions().size();
        int imageTypesize = imagesUploadInfo.getImagesType().size();
        if(size == 0) return false;

        String beforeUuid = "";
        int questionImagesInCount = 0;
        int questionImagesOutCount = 0;
        int commentaryImagesInCount = 0;
        int commentaryImagesOutCount = 0;

        for (int i = 0; i < size; i++) {
            //이미지 넣는 로직, str반환
            MultipartFile imageFile = imagesUploadInfo.getImageFiles().get(i);
            String uuid = "";
            if(uuidSize>i) uuid = imagesUploadInfo.getQuestionsUuid().get(i);
            String imageDescription = "";
            if(descriptionSize>i) imageDescription = imagesUploadInfo.getQuestionsUuid().get(i);
            String imageAttribute = "";
            if(attributesize>i) imageAttribute = imagesUploadInfo.getQuestionsUuid().get(i);
            String imageType = "";
            if(imageTypesize>i) imageType = imagesUploadInfo.getQuestionsUuid().get(i);
            if(!beforeUuid.equals(uuid)){
                beforeUuid = uuid;
                questionImagesInCount = 0;
                questionImagesOutCount = 0;
                commentaryImagesInCount = 0;
                commentaryImagesOutCount = 0;
            }

            Optional<QuestionEntity> optionalQuestion = questionsRepository.findById(uuid);
            String imageUrl ="";
            ImageDto imageDto;
            log.info("작동: "+imageType);
            switch (imageType) {
                case "questionImagesIn":
                    questionImagesInCount++;
                    imageUrl = imageService.saveImageInS3(imageFile, questionImagesInCount);
                    imageDto = ImageDto.builder()
                            .url(imageUrl)
                            .description(imageDescription)
                            .attribute(imageAttribute)
                            .build();
                    log.info(imageDto.toString());
                    optionalQuestion.ifPresent(question -> {
                        question.getQuestionImagesIn().add(imageDto);
                        questionsRepository.save(question); // 수정된 질문 저장
                    });
                    break;
                case "questionImagesOut":
                    questionImagesOutCount++;
                    imageUrl = imageService.saveImageInS3(imageFile, questionImagesOutCount);
                    imageDto = ImageDto.builder()
                            .url(imageUrl)
                            .description(imageDescription)
                            .attribute(imageAttribute)
                            .build();
                    optionalQuestion.ifPresent(question -> {
                        question.getQuestionImagesOut().add(imageDto);
                        questionsRepository.save(question); // 수정된 질문 저장
                    });
                    break;
                case "commentaryImagesIn":
                    commentaryImagesInCount++;
                    imageUrl = imageService.saveImageInS3(imageFile, commentaryImagesInCount);
                    imageDto = ImageDto.builder()
                            .url(imageUrl)
                            .description(imageDescription)
                            .attribute(imageAttribute)
                            .build();
                    optionalQuestion.ifPresent(question -> {
                        question.getCommentaryImagesIn().add(imageDto);
                        questionsRepository.save(question); // 수정된 질문 저장
                    });
                    break;
                case "commentaryImagesOut":
                    commentaryImagesOutCount++;
                    imageUrl = imageService.saveImageInS3(imageFile, commentaryImagesOutCount);
                    imageDto = ImageDto.builder()
                            .url(imageUrl)
                            .description(imageDescription)
                            .attribute(imageAttribute)
                            .build();
                    optionalQuestion.ifPresent(question -> {
                        question.getCommentaryImagesOut().add(imageDto);
                        questionsRepository.save(question); // 수정된 질문 저장
                    });
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    //Read 로직
    @Override
    public QuestionsList searchFromQuestions(Long examId, QuestionsSearchDto questionsSearchDto) {
        Query query = boolQueryBuilder.searchQuestionsQuery(examId, questionsSearchDto);

        //쿼리문 코드 적용 및 elasticSearch 통신 관련 코드
        NativeQuery searchQuery = new NativeQuery(query);
        searchQuery.setPageable(PageRequest.of(0, questionsSearchDto.getCount()));
        SearchHits<QuestionEntity> searchHits = elasticsearchTemplate.search(searchQuery, QuestionEntity.class);

        QuestionsList questionsList = new QuestionsList();
        int count = 0;
        for (SearchHit<QuestionEntity> hit : searchHits) {
            if (count >= questionsSearchDto.getCount()) {
                break; // 요청된 개수 이상의 결과가 나왔을 경우 종료
            }
            QuestionEntity entity = hit.getContent();
            Question question = Question.builder()
                    .id(entity.getId())
                    .type(entity.getType())
                    .question(entity.getQuestion())
                    .questionImagesIn(new ArrayList<>(entity.getQuestionImagesIn()))
                    .questionImagesOut(new ArrayList<>(entity.getQuestionImagesOut()))
                    .options(new ArrayList<>(entity.getOptions()))
                    .answers(new ArrayList<>(entity.getAnswers()))
                    .commentary(entity.getCommentary())
                    .commentaryImagesIn(new ArrayList<>(entity.getCommentaryImagesIn()))
                    .commentaryImagesOut(new ArrayList<>(entity.getCommentaryImagesOut()))
                    .tagsMap(new HashMap<>(entity.getTagsMap()))
                    .build();
            questionsList.add(question);
            count++;
        }

        return questionsList;
    }

    //Delete 로직
    @Override
    public boolean deleteQuestionsByExamId(Long examId) {
        // 해당 examId로 문제 삭제
        questionsRepository.deleteByExamId(examId);

        List<QuestionEntity> questions = questionsRepository.findByExamId(examId);

        // 삭제 후에 해당 examId로 조회된 데이터의 개수를 확인하여 반환
        return questions.isEmpty();
    }

    @Override
    public boolean deleteQuestionsByUuidList(List<String> uuidList) {
        // 각각의 UUID에 대해 문제를 삭제
        for (String uuid : uuidList) {
            questionsRepository.deleteById(uuid);
        }

        // 삭제 후에 해당 UUID로 조회된 데이터의 개수를 확인하여 반환
        for (String uuid : uuidList) {
            if (questionsRepository.existsById(uuid)) {
                return false; // 문제가 남아있음을 표시
            }
        }
        return true;
    }

    //test로직
    @Override
    public Long countAllQuestions() {
        return questionsRepository.count();
    }

    @Override
    public void saveQuestions(List<QuestionEntity> questionEntities) {
        questionsRepository.saveAll(questionEntities);
    }

    @Override
    public void deleteAllQuestions() {
        questionsRepository.deleteAll();
    }

    //Image관련 Service로직은 ImageService에 위임 - 추후 문제 데이터 추가시에 내용도 추가됨에 따라 QuestionsService 로직이 담당하는 부분이 많을 것으로 예상 -> 분산필요
    @Override
    public List<String> saveImages(ImagesSaveDto imagesSaveDto){
        return imageService.saveTestImagesInS3(imagesSaveDto);
    }

    @Override
    public void deleteAllImages(){
        imageService.deleteImagesInFolder();
    }
}
