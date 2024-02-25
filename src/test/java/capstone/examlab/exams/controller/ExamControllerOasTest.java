package capstone.examlab.exams.controller;

import capstone.examlab.RestDocsOpenApiSpecTest;
import capstone.examlab.exams.dto.*;
import capstone.examlab.exams.service.ExamsService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExamsController.class)
class ExamControllerOasTest extends RestDocsOpenApiSpecTest {

    @MockBean
    private ExamsService examsService;

    @Test
    void getExams() throws Exception {
        // mock getExamList method
        when(examsService.getExamList()).thenReturn(getExamListMock());

        this.mockMvc.perform(
                        get("/api/v1/exams")
                )
                .andExpect(status().isOk())
                .andDo(document("exams",
                        resource(ResourceSnippetParameters.builder()
                                .description("Get all exams")
                                .tag("exams")
                                .summary("Get all exams")
                                .responseFields(
                                        fieldWithPath("[]").description("List of exams"),
                                        subsectionWithPath("[].title").description("Exam title"),
                                        subsectionWithPath("[].sub_exams").description("List of sub exams"),
                                        fieldWithPath("[].sub_exams[].exam_id").description("Sub exam id"),
                                        fieldWithPath("[].sub_exams[].sub_title").description("Sub exam title")
                                )
                                .responseSchema(Schema.schema("ExamList"))
                                .build()
                        )
                ));
    }

    @Test
    void testGetExamType() throws Exception {
        Long examId = 1L;
        when(examsService.getExamType(examId)).thenReturn(getExamTypeMock(examId));

        this.mockMvc.perform(get("/api/v1/exams/{examId}/type", examId))
                .andExpect(status().isOk())
                .andDo(document("exam-type",
                        resource(ResourceSnippetParameters.builder()
                                .description("Get exam type")
                                .tag("exams")
                                .summary("Get exam type")
                                .pathParameters(
                                        parameterWithName("examId").description("Exam id").type(SimpleType.INTEGER)
                                )
                                .responseFields(
                                        fieldWithPath("tags").description("List of tags")
                                )
                                .responseSchema(Schema.schema("ExamType"))
                                .build()
                        )
                ));
    }

    @Test
    void testGetExamQuestions() throws Exception {
        QuestionsOption questionsOption = QuestionsOption.builder()
                .count(2)
                .tags(new ArrayList<String>() {{
                    add("상황");
                    add("표지");
                }})
                .includes(new ArrayList<String>() {{
                    add("고속도로");
                }})
                .build();

        when(examsService.getQuestionsList(questionsOption)).thenReturn(getQuestionsListMock(questionsOption));

        mockMvc.perform(get("/api/v1/exams/{examId}/questions", 1L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .queryParam("tags", "상황")
                        .queryParam("tags", "표지")
                        .queryParam("count", "2")
                        .queryParam("includes", "고속도로")) // 예시 쿼리 파라미터)
                .andExpect(status().isOk())
                .andDo(document("exam-questions",
                        resource(ResourceSnippetParameters.builder()
                                        .description("Get exam questions")
                                        .tag("exams")
                                        .summary("Get exam questions")
                                        .pathParameters(
                                                parameterWithName("examId").description("Exam id").type(SimpleType.INTEGER)
                                        )
                                        .queryParameters(
                                                parameterWithName("tags").description("List of tags").type(SimpleType.STRING).optional(),
                                                parameterWithName("count").description("Number of questions").type(SimpleType.INTEGER).optional(),
                                                parameterWithName("includes").description("List of includes").type(SimpleType.STRING).optional()
                                        )
                                        .responseFields(
                                                fieldWithPath("[]").description("List of questions"),
                                                fieldWithPath("[].id").description("Question id"),
                                                fieldWithPath("[].type").description("Question type"),
                                                fieldWithPath("[].question").description("Question"),
                                                fieldWithPath("[].question_image_urls").description("List of question image urls"),
                                                fieldWithPath("[].question_image_descriptions").description("List of question image descriptions"),
                                                fieldWithPath("[].options").description("List of options"),
                                                fieldWithPath("[].answers").description("List of answers"),
                                                fieldWithPath("[].commentary").description("Explanation"),
                                                fieldWithPath("[].commentary_image_urls").description("List of commentary image urls"),
                                                fieldWithPath("[].commentary_image_descriptions").description("List of commentary image descriptions"),
                                                fieldWithPath("[].tags").description("List of tags")
                                        )
                                        .responseSchema(Schema.schema("QuestionsList"))
                                        .build()
                        )));
    }


    private ExamList getExamListMock() {
        ExamList examList = new ExamList();

        ExamDetail examDetail1 = new ExamDetail();
        examDetail1.setTitle("운전면허");
        examDetail1.getSubExams().add(SubExamDetail.builder()
                .examId(1L)
                .subTitle("1종")
                .build());
        examDetail1.getSubExams().add(SubExamDetail.builder()
                .examId(2L)
                .subTitle("2종")
                .build());
        examList.add(examDetail1);

        ExamDetail examDetail2 = new ExamDetail();
        examDetail2.setTitle("수능");
        examDetail2.getSubExams().add(SubExamDetail.builder()
                .examId(3L)
                .subTitle("수학")
                .build());
        examDetail2.getSubExams().add(SubExamDetail.builder()
                .examId(4L)
                .subTitle("영어")
                .build());
        examList.add(examDetail2);

        return examList;
    }

    private ExamType getExamTypeMock(Long id) {
        ExamType examType = new ExamType();

        List<String> tagList = new ArrayList<>();
        tagList.add("상황");
        tagList.add("표지");
        examType.put("tags", tagList);

        return examType;
    }


    private QuestionsList getQuestionsListMock(QuestionsOption questionsOption) {
        QuestionsList questionsList = new QuestionsList();

        questionsList.add(Question.builder()
                .id(1L)
                .type("객관식")
                .question("1번 문제")
                .questionImageUrls(new ArrayList<String>() {{
                    add("1번 문제 이미지");
                }})
                .questionImageDescriptions(new ArrayList<String>() {{
                    add("1번 문제 이미지 설명");
                }})
                .options(new ArrayList<String>() {{
                    add("1번 답");
                    add("2번 답");
                    add("3번 답");
                    add("4번 답");
                }})
                .answers(new ArrayList<Integer>() {{
                    add(1);
                    add(3);
                }})
                .commentary("1번 문제 설명")
                .commentaryImageUrls(new ArrayList<String>() {{
                    add("1번 문제 설명 이미지");
                }})
                .commentaryImageDescriptions(new ArrayList<String>() {{
                    add("1번 문제 설명 이미지 설명");
                }})
                .tags(new ArrayList<String>() {{
                    add("표지");
                }})
                .build());

        questionsList.add(Question.builder()
                .id(2L)
                .type("객관식")
                .question("2번 문제")
                .questionImageUrls(new ArrayList<String>() {{
                    add("2번 문제 이미지");
                }})
                .questionImageDescriptions(new ArrayList<String>() {{
                    add("2번 문제 이미지 설명");
                }})
                .options(new ArrayList<String>() {{
                    add("1번 답");
                    add("2번 답");
                    add("3번 답");
                    add("4번 답");
                }})
                .answers(new ArrayList<Integer>() {{
                    add(2);
                    add(4);
                }})
                .commentary("2번 문제 설명")
                .commentaryImageUrls(new ArrayList<String>() {{
                    add("2번 문제 설명 이미지");
                }})
                .commentaryImageDescriptions(new ArrayList<String>() {{
                    add("2번 문제 설명 이미지 설명");
                }})
                .tags(new ArrayList<String>() {{
                    add("상황");
                }})
                .build());


        return questionsList;
    }


}
