package capstone.examlab.questions.service;

import capstone.examlab.questions.dto.ImageSaveDto;
import capstone.examlab.questions.dto.Question;
import capstone.examlab.questions.dto.QuestionsList;
import capstone.examlab.questions.dto.QuestionsOption;
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

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {
    private final QuestionsRepository questionsRepository;
    private final ImageService imageService;
    private final BoolQueryBuilder boolQueryBuilder;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public QuestionsList searchFromQuestions(Long examId, QuestionsOption questionsOption) {
        Query query = boolQueryBuilder.searchQuestionsQuery(examId, questionsOption);

        //쿼리문 코드 적용 및 elasticSearch 통신 관련 코드
        NativeQuery searchQuery = new NativeQuery(query);
        searchQuery.setPageable(PageRequest.of(0, questionsOption.getCount()));
        SearchHits<QuestionEntity> searchHits = elasticsearchTemplate.search(searchQuery, QuestionEntity.class);

        QuestionsList questionsList = new QuestionsList();
        int count = 0;
        for (SearchHit<QuestionEntity> hit : searchHits) {
            if (count >= questionsOption.getCount()) {
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
                    .tags(new ArrayList<>(entity.getTags()))
                    .build();
            questionsList.add(question);
            count++;
        }

        return questionsList;
    }

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
    public List<String> saveImages(ImageSaveDto imageSaveDto){
        return imageService.saveImagesInS3(imageSaveDto);
    }

    @Override
    public void deleteImages(){
        imageService.deleteImagesInFolder();
    }
}
