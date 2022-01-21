package com.example.jpamultitenancy.tenant.service;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: AbstractBaseService @Author: amy @Description:
 *             AbstractBaseService @Date: 2021/5/27 @Version: 1.0
 */
public abstract class AbstractBaseService<T, ID extends Serializable> implements BaseService<T, ID> {

	protected PagingAndSortingRepository<T, ID> repository;

	public PagingAndSortingRepository<T, ID> getRepository() {
		return repository;
	}

	public void setRepository(PagingAndSortingRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	public T create(T t) {
		return repository.save(t);
	}

	@Override
	public T update(T t) {
		return repository.save(t);
	}

	@Override
	public T getById(ID id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}

	@Override
	public List<T> getAllByIds(Iterable<ID> ids) {
		if (!ids.iterator().hasNext()) {
			return Collections.emptyList();
		}
		List<T> items = new ArrayList<>();
		Iterable<T> iter = repository.findAllById(ids);
		iter.forEach(item -> items.add(item));
		return items;
	}
}
