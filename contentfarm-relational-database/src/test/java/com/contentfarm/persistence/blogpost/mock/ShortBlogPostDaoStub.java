package com.contentfarm.persistence.blogpost.mock;

import com.contentfarm.persistence.blogpost.dao.IShortBlogPostDao;
import com.contentfarm.persistence.blogpost.entity.ShortBlogPostEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ShortBlogPostDaoStub implements IShortBlogPostDao {

    @Override
    public ShortBlogPostEntity getReferenceById(String id) {
        return MockShortBlogPostDatabaseTable.INSTANCE.getDatabaseTable().get(id);
    }

    @Override
    public void deleteById(String id) {
        MockShortBlogPostDatabaseTable.INSTANCE.getDatabaseTable().remove(id);
    }

    @Override
    public <S extends ShortBlogPostEntity> S save(S entity) {
        MockShortBlogPostDatabaseTable.INSTANCE.getDatabaseTable().put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ShortBlogPostEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ShortBlogPostEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<ShortBlogPostEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ShortBlogPostEntity getOne(String s) {
        return null;
    }

    @Override
    public ShortBlogPostEntity getById(String s) {
        return null;
    }

    @Override
    public <S extends ShortBlogPostEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ShortBlogPostEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ShortBlogPostEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ShortBlogPostEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ShortBlogPostEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ShortBlogPostEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ShortBlogPostEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<ShortBlogPostEntity> findOne(Specification<ShortBlogPostEntity> spec) {
        return Optional.empty();
    }

    @Override
    public List<ShortBlogPostEntity> findAll(Specification<ShortBlogPostEntity> spec) {
        return List.of();
    }

    @Override
    public Page<ShortBlogPostEntity> findAll(Specification<ShortBlogPostEntity> spec, Pageable pageable) {
        return null;
    }

    @Override
    public List<ShortBlogPostEntity> findAll(Specification<ShortBlogPostEntity> spec, Sort sort) {
        return List.of();
    }

    @Override
    public long count(Specification<ShortBlogPostEntity> spec) {
        return 0;
    }

    @Override
    public boolean exists(Specification<ShortBlogPostEntity> spec) {
        return false;
    }

    @Override
    public long delete(Specification<ShortBlogPostEntity> spec) {
        return 0;
    }

    @Override
    public <S extends ShortBlogPostEntity, R> R findBy(Specification<ShortBlogPostEntity> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ShortBlogPostEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<ShortBlogPostEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<ShortBlogPostEntity> findAll() {
        return List.of();
    }

    @Override
    public List<ShortBlogPostEntity> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }



    @Override
    public void delete(ShortBlogPostEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends ShortBlogPostEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ShortBlogPostEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<ShortBlogPostEntity> findAll(Pageable pageable) {
        return null;
    }
}
