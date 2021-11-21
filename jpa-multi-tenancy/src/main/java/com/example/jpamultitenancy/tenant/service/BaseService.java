package com.example.jpamultitenancy.tenant.service;

import java.io.Serializable;
import java.util.List;

/** @ClassName: BaseService @Author: amy @Description: BaseService @Date: 2021/5/27 @Version: 1.0 */
public interface BaseService<T, ID extends Serializable> {

  T create(T t);

  T update(T t);

  T getById(ID id);

  void deleteById(ID id);

  List<T> getAllByIds(Iterable<ID> ids);
}
