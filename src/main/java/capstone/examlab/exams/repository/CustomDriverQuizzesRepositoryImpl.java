package capstone.examlab.exams.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomDriverQuizzesRepositoryImpl implements CustomDriverQuizzesRepository{
        private final JPAQueryFactory jpaQueryFactory;

    //querydsl 코드 작성
}
