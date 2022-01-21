package com.example.sm.service;

import com.example.sm.entity.BaseModel;
import com.example.sm.entity.PageInfo;
import com.example.sm.entity.PageResponse;

import java.util.List;

/**
 * {@link BaseCrudService}
 *
 * @author Liyaohui
 * @date 7/15/21
 */
public interface BaseCrudService<E extends BaseModel> {

	/**
	 * query by id
	 *
	 * @param id primary key
	 * @return mapping entity
	 */
	E get(Long id);

	/**
	 * query one by params
	 *
	 * @param entity param entity
	 * @return mapping entity
	 */
	E get(E entity);

	/**
	 * query list by params
	 *
	 * @param entity param entity
	 * @return mapping entity list
	 */
	List<E> find(E entity);

	/**
	 * query page list by params
	 *
	 * @param entity   entity params
	 * @param pageInfo page params
	 * @return page data list
	 */
	PageResponse<E> find(E entity, PageInfo pageInfo);

	/**
	 * add value by param
	 *
	 * @param entity params
	 */
	void add(E entity);

	/**
	 * update value by param
	 *
	 * @param entity params
	 */
	void update(E entity);

	/**
	 * update value selective
	 *
	 * @param entity     params
	 * @param ignoreNull ignore null column(in params)
	 */
	void update(E entity, boolean ignoreNull);

	/**
	 * delete by params
	 *
	 * @param entity params
	 */
	int remove(E entity);

}
