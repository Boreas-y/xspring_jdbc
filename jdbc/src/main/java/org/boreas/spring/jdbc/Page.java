package org.boreas.spring.jdbc;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4159616212548459962L;

	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int DEFAULT_PAGINATION = 1;

	private int pageSize = DEFAULT_PAGE_SIZE;
	private int pagination = DEFAULT_PAGINATION;
	private int totalPage = 0;
	private int totalRows = 0;

	private List<T> beans;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPagination() {
		return pagination;
	}

	public void setPagination(int pagination) {
		this.pagination = pagination;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<T> getBeans() {
		return beans;
	}

	public void setBeans(List<T> beans) {
		this.beans = beans;
	}

}
