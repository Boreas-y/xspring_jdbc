package org.boreas.spring.jdbc.query.executor;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Basic implementation of {@code QueryExecutor} which query for a single bean.
 * 
 * @author boreas
 *
 * @param <T>
 */
public class BeanQueryExecutor<T> extends AbstractQueryExecutor<T> {
	private RowMapper<T> rowMapper;

	public BeanQueryExecutor(JdbcTemplate jdbcTemplate, Class<T> type) {
		super(jdbcTemplate);
		this.rowMapper = new BeanPropertyRowMapper<T>(type);
	}

	@Override
	public T excute(String recordSQL, String countSQL, Object[] args) {
		return getJdbcTemplate().queryForObject(recordSQL, args, rowMapper);
	}

}
