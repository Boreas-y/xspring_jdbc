package org.boreas.spring.jdbc.query.executor;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ListQueryExecutor<T> extends AbstractQueryExecutor<List<T>> {
	private RowMapper<T> rowMapper;

	public ListQueryExecutor(JdbcTemplate jdbcTemplate, Class<T> type) {
		super(jdbcTemplate);
		this.rowMapper = new BeanPropertyRowMapper<T>(type);
	}

	@Override
	public List<T> excute(String recordSQL, String countSQL, Object[] args) {
		return getJdbcTemplate().query(recordSQL, args, rowMapper);
	}

}
