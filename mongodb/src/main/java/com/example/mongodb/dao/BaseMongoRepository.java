package com.example.mongodb.dao;

import com.example.mongodb.entity.BaseModel;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * {@link BaseMongoRepository}
 *
 * @author Liyaohui
 * @date 5/31/21
 */
public interface BaseMongoRepository<T extends BaseModel> {

  T findOne(T entity, Query query);

  List<T> find(T entity, Query query);

  long count(T entity, Query query);

  void insert(T entity);

  void insertMany(List<T> entities);

  void save(T entity);

  void updateOneSelective(T entity);

  void updateOne(T entity, Query query, Update update);

  void updateMulti(T entity, Query query, Update update);

  void remove(T entity);

  void removeByQuery(Query query, T entity);
}
