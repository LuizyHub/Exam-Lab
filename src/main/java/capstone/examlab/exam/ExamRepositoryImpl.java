package capstone.examlab.exam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public class ExamRepositoryImpl implements ExamRepository {
    //아래 메서드 공부+수정필요
    @Override
    public Page<Quiz> searchSimilar(Quiz entity, String[] fields, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Quiz> S save(S entity, RefreshPolicy refreshPolicy) {
        return null;
    }

    @Override
    public <S extends Quiz> Iterable<S> saveAll(Iterable<S> entities, RefreshPolicy refreshPolicy) {
        return null;
    }

    @Override
    public void deleteById(Long aLong, RefreshPolicy refreshPolicy) {

    }

    @Override
    public void delete(Quiz entity, RefreshPolicy refreshPolicy) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs, RefreshPolicy refreshPolicy) {

    }

    @Override
    public void deleteAll(Iterable<? extends Quiz> entities, RefreshPolicy refreshPolicy) {

    }

    @Override
    public void deleteAll(RefreshPolicy refreshPolicy) {

    }

    @Override
    public <S extends Quiz> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Quiz> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Quiz> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Quiz> findAll() {
        return null;
    }

    @Override
    public Iterable<Quiz> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Quiz entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Quiz> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Iterable<Quiz> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return null;
    }
}
