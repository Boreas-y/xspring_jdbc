package org.boreas.spring.jdbc.query.executor;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractQueryExecutor<T> implements QueryExecutor<T> {
	private JdbcTemplate jdbcTemplate;

	public AbstractQueryExecutor(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

	}

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
