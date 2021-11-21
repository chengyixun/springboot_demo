package com.example.mongodb.service;

import com.example.mongodb.entity.BaseModel;
import com.example.mongodb.entity.PageInfo;
import com.example.mongodb.entity.PageResponse;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * {@link BaseMongoService} mongodb base crud service
 *
 * @author <a href="mailto:tangtongda@gmail.com">Tino.Tang</a>
 * @version ${project.version} - 2020/9/8
 */
public interface BaseMongoService<E extends BaseModel> {

  /**
   * @param entity mapping entity
   * @param query spring data mongo query
   * @return result
   */
  E get(E entity, Query query);

  /**
   * @param entity mapping entity
   * @param query spring data mongo query
   * @return result
   */
  List<E> find(E entity, Query query);

  /**
   * page query
   *
   * @param entity entity
   * @param query query param
   * @param pageInfo page param
   * @return para result list
   */
  PageResponse<E> find(E entity, Query query, PageInfo pageInfo);

  /**
   * @param entity mapping entity
   * @param query spring data mongo query
   * @return result
   */
  long count(E entity, Query query);

  /** @param entity mapping entity */
  void add(E entity);

  /** @param entity mapping entity */
  void update(E entity);

  /**
   * update data by id with ignore
   *
   * @param entity entity
   * @param ignoreNull true ignore null,false not ignore null
   */
  void update(E entity, boolean ignoreNull);

  /** @param entity mapping entity */
  void remove(E entity);
}
