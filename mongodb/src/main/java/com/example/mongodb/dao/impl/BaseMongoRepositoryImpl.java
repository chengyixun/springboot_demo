package com.example.mongodb.dao.impl;

import com.example.mongodb.common.helper.MongoHelper;
import com.example.mongodb.dao.BaseMongoRepository;
import com.example.mongodb.entity.BaseModel;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * {@link BaseMongoRepositoryImpl}
 *
 * @author Liyaohui
 * @date 5/31/21
 */
public abstract class BaseMongoRepositoryImpl<E extends BaseModel>
    implements BaseMongoRepository<E> {

  /** mongodb transaction switch:true-on,false-off */
  protected static Boolean transactionEnabled = false;

  @Autowired protected MongoTemplate mongoTemplate;

  public BaseMongoRepositoryImpl<E> setMongoTemplate(MongoTemplate template) {
    mongoTemplate = template;
    if (transactionEnabled && null != mongoTemplate) {
      // turn on the mongondb transaction switch
      mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);
    }
    return this;
  }

  @Override
  public E findOne(E entity, Query query) {
    String collectionName = MongoHelper.getCollectionName(entity);
    if (null == query) {
      query = new Query();
    }
    return mongoTemplate.findOne(query, MongoHelper.getEntityClass(entity), collectionName);
  }

  @Override
  public List<E> find(E entity, Query query) {
    String collectionName = MongoHelper.getCollectionName(entity);
    if (null == query) {
      return mongoTemplate.findAll(MongoHelper.getEntityClass(entity), collectionName);
    }
    return mongoTemplate.find(query, MongoHelper.getEntityClass(entity), collectionName);
  }

  @Override
  public long count(E entity, Query query) {
    //  when the mongodb transaction is on, this will not supported on count sql
    if (transactionEnabled) {
      // the replace way is getting the result data size to count,bus this is unstable
      return this.find(entity, query).size();
    }
    String collectionName = MongoHelper.getCollectionName(entity);
    if (null == query) {
      query = new Query();
    }
    return mongoTemplate.count(query, MongoHelper.getEntityClass(entity), collectionName);
  }

  @Override
  public void insert(E entity) {
    //  entity.setId(); id新增方式
    mongoTemplate.save(entity, MongoHelper.getCollectionName(entity));
  }

  @Override
  public void insertMany(List<E> entities) {
    if (CollectionUtils.isEmpty(entities)) {
      return;
    }
    mongoTemplate.insert(entities, MongoHelper.getCollectionName(entities.get(0)));
  }

  @Override
  public void save(E entity) {
    mongoTemplate.save(entity, MongoHelper.getCollectionName(entity));
  }

  @Override
  public void updateOneSelective(E entity) {
    Update update = new Update();
    Class<E> aClass = MongoHelper.getEntityClass(entity);
    for (Field field : aClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (null != field.get(entity)) {
          update.set(field.getName(), field.get(entity));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    mongoTemplate.findAndModify(
        Query.query(Criteria.where(DBCollection.ID_FIELD_NAME).is(entity.getId())),
        update,
        MongoHelper.getEntityClass(entity));
  }

  @Override
  public void updateOne(E entity, Query query, Update update) {
    mongoTemplate.updateFirst(query, update, MongoHelper.getCollectionName(entity));
  }

  @Override
  public void updateMulti(E entity, Query query, Update update) {
    mongoTemplate.updateMulti(query, update, MongoHelper.getCollectionName(entity));
  }

  @Override
  public void remove(E entity) {
    mongoTemplate.remove(entity, MongoHelper.getCollectionName(entity));
  }

  @Override
  public void removeByQuery(Query query, E entity) {
    mongoTemplate.remove(
        query, MongoHelper.getEntityClass(entity), MongoHelper.getCollectionName(entity));
  }
}
