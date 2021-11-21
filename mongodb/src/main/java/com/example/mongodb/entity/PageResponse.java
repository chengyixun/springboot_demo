package com.example.mongodb.entity;

import java.util.List;

/**
 * {@link PageResponse} page query response bean
 *
 * @author <a href="mailto:tangtongda@gmail.com">Tino.Tang</a>
 * @version ${project.version} - 2020/10/19
 */
public class PageResponse<T> extends PageInfo {
  /** 列表数据 */
  private List<T> list;

  /** 总条数 */
  private long total;

  /** 总页数 */
  private int pages;

  /**
   * github page constructor
   *
   * @param page github page
   */
  public PageResponse(com.github.pagehelper.Page<T> page) {
    super(page.getPageNum(), page.getPageSize());
    this.list = page.getResult();
    this.total = page.getTotal();
    this.pages = page.getPages();
  }

  /**
   * spring data page over to PageResponse
   *
   * @param page spring data page
   */
  public PageResponse(org.springframework.data.domain.Page<T> page) {
    // Mongo page number start from 0
    super(page.getNumber() + 1, page.getSize());
    this.list = page.getContent();
    this.total = page.getTotalElements();
    this.pages = page.getTotalPages();
  }

  /**
   * query result page over to PageResponse
   *
   * @param list data list
   * @param pageNum page num
   * @param pageSize page size
   * @param total total
   * @param pages total pages
   */
  public PageResponse(List<T> list, int pageNum, int pageSize, long total, int pages) {
    super(pageNum, pageSize);
    this.list = list;
    this.total = total;
    this.pages = pages;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }
}
