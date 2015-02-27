package org.boreas.spring.jdbc;

import org.boreas.spring.jdbc.query.Query;
import org.springframework.util.Assert;

public class PageQuery {

	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int DEFAULT_PAGINATION = 1;

	private int pageSize = DEFAULT_PAGE_SIZE;
	private int pagination = DEFAULT_PAGINATION;
	private String countSql = "";
	private String recordSql = "";
	private Object[] args = new Object[] {};

	public PageQuery() {
	}

	public PageQuery(int pagination) {
		setPagination(pagination);
	}

	public PageQuery(int pagination, int pageSize) {
		setPageSize(pageSize);
		setPagination(pagination);
	}

	public PageQuery(Query query) {
		setQuery(query);
	}

	public PageQuery(Query query, int pagination) {
		setQuery(query);
		setPagination(pagination);
	}

	public PageQuery(Query query, int pagination, int pageSize) {
		setQuery(query);
		setPageSize(pageSize);
		setPagination(pagination);
	}

	public void setQuery(Query query) {
		Assert.notNull(query, "query must not be null.");
		countSql = query.buildCountQuery();
		recordSql = query.buildRecordQuery();
		args = query.args();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		Assert.isTrue(pageSize > 0, "pageSize must be positive.");
		this.pageSize = pageSize;
	}

	public int getPagination() {
		return pagination;
	}

	public void setPagination(int pagination) {
		Assert.isTrue(pagination > 0, "pagination must be positive.");
		this.pagination = pagination;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getRecordSql() {
		return recordSql;
	}

	public void setRecordSql(String recordSql) {
		this.recordSql = recordSql;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return new StringBuilder("PageQuery[pagination:").append(pagination)
				.append(";pageSize:").append(pageSize).append("]").toString();
	}
}
