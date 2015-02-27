package org.boreas.spring.jdbc.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.boreas.spring.util.StringUtils;

public abstract class AbstractGroup implements Sql, Group {

	protected final Query query;
	protected final List<String> groupBy;

	public AbstractGroup(final Query query) {
		this.query = query;
		query.group = this;
		groupBy = new ArrayList<String>(5);
	}

	@Override
	public Query group(String... fields) {
		groupBy.addAll(Arrays.asList(fields));
		return query;
	}

	@Override
	public StringBuilder buildSql() {
		StringBuilder sql = new StringBuilder();
		if (!groupBy.isEmpty()) {
			sql.append(" GROUP BY ").append(StringUtils.combine(",", groupBy));
		}
		return sql;
	}

}
