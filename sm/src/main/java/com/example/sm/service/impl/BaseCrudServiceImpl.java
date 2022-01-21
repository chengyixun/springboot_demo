package com.example.sm.service.impl;

import com.example.sm.entity.BaseModel;
import com.example.sm.entity.PageInfo;
import com.example.sm.entity.PageResponse;
import com.example.sm.repository.BaseCrudRepository;
import com.example.sm.service.BaseCrudService;
import com.github.pagehelper.Page;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * {@link BaseCrudServiceImpl}
 *
 * @author Liyaohui
 * @date 7/19/21
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseCrudServiceImpl<R extends BaseCrudRepository<E>, E extends BaseModel>
		implements BaseCrudService<E> {
	private static final int DEFAULT_PAGE_NUM = 1;
	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int MAX_PAGE_SIZE = 2000;
	private static final char SEPARATOR = '_';
	private static final String NUMERIC_COLUMN_FLAG = "-";

	@Autowired
	protected R dao;

	@Autowired
	private PageHelperProperties pageHelperProperties;

	@Override
	public E get(Long id) {
		if (Objects.isNull(id)) {
			throw new RuntimeException("id is null");
		}
		E entity = dao.selectByPrimaryKey(id);
		if (Objects.isNull(entity)) {
			throw new RuntimeException("query result is null");
		}
		return entity;
	}

	@Override
	public E get(E entity) {
		entity = dao.selectOne(entity);
		return entity;
	}

	@Override
	public List<E> find(E entity) {
		List<E> results = dao.select(entity);
		if (CollectionUtils.isEmpty(results)) {
			return new ArrayList<>();
		}
		return results;
	}

	// ????
	@Override
	public PageResponse<E> find(E entity, PageInfo pageInfo) {
		Page<E> page = startPage(pageInfo);
		dao.select(entity);
		return new PageResponse<E>(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(E entity) {
		entity.preInsert();
		int result = dao.insertSelective(entity);
		if (result <= 0) {
			throw new RuntimeException("insert error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(E entity) {
		this.update(entity, true);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(E entity, boolean ignoreNull) {
		entity.preUpdate();
		int result;
		if (ignoreNull) {
			result = dao.updateByPrimaryKeySelective(entity);
		} else {
			result = dao.updateByPrimaryKey(entity);
		}
		if (result <= 0) {
			throw new RuntimeException("update error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int remove(E entity) {
		return dao.delete(entity);
	}

	/**
	 * 开启分页
	 *
	 * @param pageInfo
	 * @return
	 */
	protected Page<E> startPage(PageInfo pageInfo) {
		int pageNum = getReasonablePageNum(pageInfo.getPageNum());
		int pageSize = getReasonablePageSize(pageInfo.getPageSize());
		String orderBy = getOrderBy(pageInfo.getOrderBy());
		return com.github.pagehelper.page.PageMethod.startPage(pageNum, pageSize, orderBy);
	}

	/**
	 * 获取有效的页数
	 *
	 * @param pageNum 页数
	 * @return
	 */
	private int getReasonablePageNum(Integer pageNum) {
		if (pageNum == null || pageNum <= 0) {
			pageNum = DEFAULT_PAGE_NUM;
		}
		return pageNum;
	}

	/**
	 * 获取有效的每页数据
	 *
	 * @param pageSize
	 * @return
	 */
	private int getReasonablePageSize(Integer pageSize) {
		if (pageSize == null || pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		return pageSize;
	}

	/**
	 * 处理排序
	 *
	 * @param orderBy 排序规则
	 * @return
	 */
	private String getOrderBy(Map<String, String> orderBy) {
		if (CollectionUtils.isEmpty(orderBy)) {
			return null;
		}

		StringBuilder orderByBuilder = new StringBuilder();
		orderBy.forEach((key, value) -> {
			orderByBuilder.append(",");
			String column = toUnderScoreCase(key);
			// 以"-"开头的字段名需要转成数字
			if (column.startsWith(NUMERIC_COLUMN_FLAG)) {
				column = getNumericColumn(column.substring(1));
			}
			orderByBuilder.append(column);
			if (Sort.Direction.DESC.toString().equalsIgnoreCase(value)) {
				orderByBuilder.append(" ");
				orderByBuilder.append(Sort.Direction.DESC);
			}
		});

		return orderByBuilder.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase(" hello_world ") == "helloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 转成数字格式
	 *
	 * @param column 字段
	 * @return 字段
	 */
	private String getNumericColumn(String column) {
		switch (pageHelperProperties.getHelperDialect()) {
		case "mysql":
			return "(" + column + " + 0)";
		case "oracle":
			return "TO_NUMBER(REGEXP_SUBSTR(" + column + ",'^[0-9]+'))";
		case "sqlserver":
			return "CASE ISNUMERIC(" + column + ") WHEN '1' THEN (" + column + " + 0) ELSE NULL END";
		default:
			return column;
		}
	}
}
