package capstone.examlab.questions.service;


import capstone.examlab.questions.dto.ImageSaveDto;
import capstone.examlab.questions.dto.Question;
import capstone.examlab.questions.dto.QuestionsList;
import capstone.examlab.questions.dto.QuestionsOption;
import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.repository.BoolQueryBuilder;
import capstone.examlab.questions.repository.DriverLicenseQuestionsRepository;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.elasticsearch.client.elc.Queries.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {
    private final DriverLicenseQuestionsRepository driverLicenseQuestionsRepository;
    private final ImageService imageService;
    private final BoolQueryBuilder boolQueryBuilder;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public QuestionsList testFind(Long examId, QuestionsOption questionsOption) {
        Query query = boolQueryBuilder.buildBoolQuery();

        //이전에 따로 빈에 등록했던 방식과 다르게
        NativeQuery searchQuery = new NativeQuery(query);
        searchQuery.setPageable(PageRequest.of(0, questionsOption.getCount()));
        SearchHits<QuestionEntity> searchHits = elasticsearchTemplate.search(searchQuery, QuestionEntity.class);

        QuestionsList questionsList = new QuestionsList();
        for (SearchHit<QuestionEntity> hit : searchHits) {
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
                    .tags(new ArrayList<>(entity.getTags()))
                    .build();
            questionsList.add(question);
        }

        return questionsList;
    }
    @Override
    public QuestionsList findByDriverLicenseQuestions(Long examId, QuestionsOption questionsOption) {
        Pageable pageable = PageRequest.of(0, questionsOption.getCount());
        Page<QuestionEntity> questionsPage;
        if(questionsOption.getTags()==null&questionsOption.getIncludes()==null){
            questionsPage = driverLicenseQuestionsRepository.findAll(pageable);
        }
        else if(questionsOption.getTags()==null){
            questionsPage = driverLicenseQuestionsRepository.findByQuestionContainingOrOptionsContaining(questionsOption.getIncludes(), questionsOption.getIncludes(), pageable);
        }

         else if(questionsOption.getIncludes()==null) {
            log.info("작동하고있음");
            log.info(questionsOption.getTags().toString());
            Query query = boolQueryBuilder.buildBoolQuery();
           // questionsPage = driverLicenseQuestionsRepository.search(query, pageable);
            questionsPage = driverLicenseQuestionsRepository.findByTagsIn(questionsOption.getTags(), pageable);
        }

        else {
            questionsPage = driverLicenseQuestionsRepository.findByTagsInAndQuestionContainingOrOptionsContaining(questionsOption.getTags(), questionsOption.getIncludes(), questionsOption.getIncludes(), pageable);
        }

        QuestionsList questionsList = new QuestionsList();
        for (QuestionEntity entity : questionsPage) {
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
                    .tags(new ArrayList<>(entity.getTags()))
                    .build();
            questionsList.add(question);
        }
        return questionsList;
    }

    @Override
    public QuestionsList findAllByDriverLicneseQuestions() {
        Iterable<QuestionEntity> questionsPage;
        QuestionsList questionsList = new QuestionsList();
        questionsPage = driverLicenseQuestionsRepository.findAll();

        for (QuestionEntity entity : questionsPage) {
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
                    .tags(new ArrayList<>(entity.getTags()))
                    .build();
            questionsList.add(question);
        }
        return questionsList;
    }

    @Override
    public Long countAllByDriverLicenseQuestions() {
        return driverLicenseQuestionsRepository.count();
    }

    @Override
    public void saveDriverLicenseQuestions(List<QuestionEntity> questionEntities) {
        driverLicenseQuestionsRepository.saveAll(questionEntities);
    }

    @Override
    public void deleteDriverLicenseQuestions() {
        driverLicenseQuestionsRepository.deleteAll();
    }

    //Image관련 Service로직은 ImageService에 위임 - 추후 문제 데이터 추가시에 내용도 추가됨에 따라 QuestionsService 로직이 담당하는 부분이 많을 것으로 예상 -> 분산필요
    @Override
    public List<String> saveImages(ImageSaveDto imageSaveDto){
        return imageService.saveImagesInS3(imageSaveDto);
    }

    @Override
    public void deleteImages(){
        imageService.deleteImagesInFolder();
    }
}
