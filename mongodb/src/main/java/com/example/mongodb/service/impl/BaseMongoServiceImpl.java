package com.example.mongodb.service.impl;

import com.example.mongodb.common.constants.Constants;
import com.example.mongodb.dao.BaseMongoRepository;
import com.example.mongodb.dao.impl.BaseServiceImpl;
import com.example.mongodb.entity.BaseModel;
import com.example.mongodb.entity.PageInfo;
import com.example.mongodb.entity.PageResponse;
import com.example.mongodb.service.BaseMongoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link BaseMongoServiceImpl} spring data mongodb base crud implements
 *
 * @author <a href="mailto:tangtongda@gmail.com">Tino.Tang</a>
 * @version ${project.version} - 2020/9/8
 */
public abstract class BaseMongoServiceImpl<R extends BaseMongoRepository<E>, E extends BaseModel>
    extends BaseServiceImpl implements BaseMongoService<E> {
  private static final int DEFAULT_PAGE_NUM = 1;

  private static final int MAX_PAGE_SIZE = 2000;

  private static final int DEFAULT_PAGE_SIZE = 20;

  @Autowired protected R dao;

  @Override
  public E get(E entity, Query query) {
    return dao.findOne(entity, query);
  }

  @Override
  public List<E> find(E entity, Query query) {
    return dao.find(entity, query);
  }

  @Override
  public PageResponse<E> find(E entity, Query query, PageInfo pageInfo) {
    PageRequest pageRequest = startPage(pageInfo);
    if (query == null) {
      query = new Query();
    }
    long total = dao.count(entity, query);
    int pages =
        (int) (total / pageInfo.getPageSize() + (total % pageInfo.getPageSize() > 0 ? 1 : 0));
    List<E> list = dao.find(entity, query.with(pageRequest));

    return new PageResponse<>(
        list, pageRequest.getPageNumber(), pageRequest.getPageSize(), total, pages);
  }

  @Override
  public long count(E entity, Query query) {
    return dao.count(entity, query);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(E entity) {
    entity.preInsert();
    // entity.setId(IdGenKit.getID());
    dao.insert(entity);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(E entity) {
    try {
      entity.preUpdate();
      dao.save(entity);
    } catch (OptimisticLockingFailureException e) {
      // RepositoryException.updateException();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(E entity, boolean ignoreNull) {
    try {
      entity.preUpdate();
      if (ignoreNull) {
        dao.updateOneSelective(entity);
      } else {
        dao.save(entity);
      }
    } catch (OptimisticLockingFailureException e) {
      // RepositoryException.updateException();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void remove(E entity) {
    try {
      dao.remove(entity);
    } catch (OptimisticLockingFailureException e) {
      // RepositoryException.updateException();
    }
  }

  /**
   * 获取默认排序方式
   *
   * @param orderBy order by param
   * @return order by script
   */
  protected String getDefaultOrderBy(String orderBy) {
    if (StringUtils.isBlank(orderBy)) {
      return "createTime" + Constants.DESC;
    }
    return orderBy;
  }

  /**
   * 开启分页
   *
   * @param pageInfo page info
   * @return page request
   */
  protected PageRequest startPage(PageInfo pageInfo) {
    int pageNum = getReasonablePageNum(pageInfo.getPageNum()) - 1;
    int pageSize = getReasonablePageSize(pageInfo.getPageSize());

    if (!CollectionUtils.isEmpty(pageInfo.getOrderBy())) {
      List<Sort.Order> orderList = new ArrayList<>();
      pageInfo
          .getOrderBy()
          .forEach(
              (key, value) -> {
                Sort.Order order;
                if (Sort.Direction.DESC.toString().equalsIgnoreCase(value)) {
                  order = Sort.Order.desc(key);
                } else {
                  order = Sort.Order.asc(key);
                }
                orderList.add(order);
              });
      Sort sort = Sort.by(orderList);
      return PageRequest.of(pageNum, pageSize, sort);
    }

    return PageRequest.of(pageNum, pageSize);
  }

  /**
   * 获取有效的页数
   *
   * @param pageNum page number
   * @return usable page number
   */
  private int getReasonablePageNum(Integer pageNum) {
    if (pageNum == null || pageNum <= 0) {
      pageNum = DEFAULT_PAGE_NUM;
    }
    return pageNum;
  }

  /**
   * 获取有效的每页数据数
   *
   * @param pageSize 分页大小
   * @return usable pageSize
   */
  private int getReasonablePageSize(Integer pageSize) {
    if (pageSize == null || pageSize <= 0) {
      pageSize = DEFAULT_PAGE_SIZE;
    } else if (pageSize > MAX_PAGE_SIZE) {
      pageSize = MAX_PAGE_SIZE;
    }
    return pageSize;
  }
}
