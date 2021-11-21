package com.example.jpamultitenancy.tenant.service.impl;

import com.example.jpamultitenancy.tenant.service.BaseService;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: BaseServiceImpl @Author: amy @Description: 基础serviceImpl @Date: 2021/5/27 @Version:
 * 1.0
 */
public class BaseServiceImpl<R extends JpaRepository<T, ID>, T> implements BaseService<T, ID> {

  @Autowired protected R baseRepository;

  @Override
  public T create(T t) {
    return baseRepository.save(t);
  }

  @Override
  public T update(T t) {
    return baseRepository.save(t);
  }

  @Override
  public T getById(ID id) {
    return baseRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
  }

  @Override
  public void deleteById(ID id) {
    baseRepository.deleteById(id);
  }

  @Override
  public List<T> getAllByIds(Iterable<ID> ids) {
    if (!ids.iterator().hasNext()) {
      return Collections.emptyList();
    }
    List<T> items = new ArrayList<>();
    Iterable<T> iter = baseRepository.findAllById(ids);
    iter.forEach(item -> items.add(item));
    return items;
  }
}
