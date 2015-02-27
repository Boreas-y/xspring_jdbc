package org.boreas.spring.jdbc.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.boreas.spring.util.StringUtils;

public abstract class AbstractCondition implements Sql, Condition {
	private final List<String> sqls;
	private final List<Object> args;
	protected final Query query;

	public AbstractCondition(final Query query) {
		sqls = Collections.synchronizedList(new LinkedList<String>());
		args = Collections.synchronizedList(new LinkedList<Object>());
		this.query = query;
		query.condition = this;
	}

	@Override
	public Query eq(String fieldName, Object value) {
		return compare(fieldName, value, CompareType.EQUAL);
	}

	@Override
	public Query ne(String fieldName, Object value) {
		return compare(fieldName, value, CompareType.NOT_EQUAL);
	}

	@Override
	public Query lt(String fieldName, Object value) {
		return compare(fieldName, value, CompareType.LESS_THAN);
	}

	@Override
	public Query gt(String fieldName, Object value) {
		return compare(fieldName, value, CompareType.GREATER_THAN);
	}

	@Override
	public Query le(String fieldName, Object value) {
		return compare(fieldName, value, CompareType.LESS_EQUAL);
	}

	@Override
	public Query ge(String fieldName, Object value) {
		return compare(fieldName, value, CompareType.GREATER_EQUAL);
	}

	@Override
	public Query between(String fieldName, Object min, Object max) {
		if (min != null && max != null) {
			setValue(fieldName + " BETWEEN ? AND ?", min, max);
		}
		return query;
	}

	@Override
	public Query contains(String fieldName, Object value) {
		if (!checkNullValue(value))
			setValue(fieldName + " like ?", "%" + value.toString() + "%");
		return query;
	}

	@Override
	public Query nil(String fieldName) {
		setValue(fieldName + " is null");
		return query;
	}

	@Override
	public Query notNil(String fieldName) {
		setValue(fieldName + " is not null");
		return query;
	}

	@Override
	public Query customize(String condition, Object... args) {
		setValue(condition, args);
		return query;
	}

	final void addArgs(Object... args) {
		if (args != null)
			synchronized (this) {
				this.args.addAll(Arrays.asList(args));
			}
	}

	/**
	 * Build the {@code where} clause. All conditions will be combined with
	 * {@code and}. If some conditions need to be combined with {@code or}, it
	 * is recommended to use {@link #customize(String, Object...)}.
	 */
	@Override
	public StringBuilder buildSql() {
		StringBuilder sql = new StringBuilder();
		if (sqls.size() > 0)
			sql.append(" WHERE ").append(StringUtils.combine(" AND ", sqls));
		return sql;
	}

	/**
	 * Get all arguments defined in conditions.
	 * 
	 * @return
	 */
	@Override
	public Object[] args() {
		return args.toArray();
	}

	protected Query compare(String fieldName, Object value, CompareType type) {
		if (!checkNullValue(value))
			setValue(fieldName + type + "?", value);
		return query;
	}

	/**
	 * add SQL string and argument into lists synchronically.
	 * 
	 * @param sql
	 *            a fragment of SQL
	 * @param args
	 *            arguments to be insert
	 */
	protected final void setValue(final String sql, final Object... args) {
		synchronized (this) {
			sqls.add(sql);
			if (args != null)
				this.args.addAll(Arrays.asList(args));
		}
	}

	/**
	 * Check if the given value or {@code toString} is null. If the value
	 * 
	 * @param value
	 * @return {@code true} if the value is null
	 */
	protected final boolean checkNullValue(final Object value) {
		return value == null || !StringUtils.hasText(value.toString());
	}

	private enum CompareType {
		EQUAL("="), LESS_THAN("<"), GREATER_THAN(">"), LESS_EQUAL("<="), GREATER_EQUAL(
				">="), NOT_EQUAL("!=");
		private String signal;

		private CompareType(String signal) {
			this.signal = signal;
		}

		@Override
		public String toString() {
			return signal;
		}
	}
}
