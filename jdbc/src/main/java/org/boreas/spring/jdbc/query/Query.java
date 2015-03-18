package org.boreas.spring.jdbc.query;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.boreas.spring.jdbc.query.executor.QueryExecutor;
import org.boreas.spring.jdbc.query.oracle.OracleQuery;

/**
 * This class is used to simplify creation of some simple {@code select} SQL. It
 * provides some common methods to make code more readable and less fallible. It
 * also offers chain invocation to simplify the code. Implemention of this class
 * need to implement {@link #buildRecordQuery()} and {@link #buildCountQuery()}.<br>
 * <b>Note</b>: This class doesn't offer validation for SQL.
 * 
 * @author boreas
 * @see QueryFactory
 */
public class Query implements Select, Condition, Order, Group {

	protected AbstractSelect select;
	protected AbstractCondition condition;
	protected AbstractOrder order;
	protected AbstractGroup group;
	private final static Log LOG = LogFactory.getLog(Query.class);

	@Override
	public Query check(String tableName, String alias) {
		return select.check(tableName, alias);
	}

	@Override
	public Query select(String tableName, String alias, String... fields) {
		return select.select(tableName, alias, fields);
	}

	@Override
	public Query select(Query query, String alias, String... fields) {
		synchronized (this) {
			select.select(query, alias, fields);
			condition.addArgs(query.args());
		}
		return this;
	}

	@Override
	public Join left(String tableName, String alias) {
		return select.left(tableName, alias);
	}

	@Override
	public Join left(Query query, String alias) {
		return select.left(query, alias);
	}

	@Override
	public Join right(String tableName, String alias) {
		return select.right(tableName, alias);
	}

	@Override
	public Join right(Query query, String alias) {
		return select.right(query, alias);
	}

	@Override
	public Join full(String tableName, String alias) {
		return select.full(tableName, alias);
	}

	@Override
	public Join full(Query query, String alias) {
		return select.full(query, alias);
	}

	@Override
	public Join cross(String tableName, String alias) {
		return select.cross(tableName, alias);
	}

	@Override
	public Join cross(Query query, String alias) {
		return select.cross(query, alias);
	}

	@Override
	public Join natural(String tableName, String alias) {
		return select.natural(tableName, alias);
	}

	@Override
	public Join natural(Query query, String alias) {
		return select.natural(query, alias);
	}

	@Override
	public Query eq(String fieldName, Object value) {
		return condition.eq(fieldName, value);
	}

	@Override
	public Query ne(String fieldName, Object value) {
		return condition.ne(fieldName, value);
	}

	@Override
	public Query lt(String fieldName, Object value) {
		return condition.lt(fieldName, value);
	}

	@Override
	public Query gt(String fieldName, Object value) {
		return condition.gt(fieldName, value);
	}

	@Override
	public Query le(String fieldName, Object value) {
		return condition.le(fieldName, value);
	}

	@Override
	public Query ge(String fieldName, Object value) {
		return condition.ge(fieldName, value);
	}

	@Override
	public Query between(String fieldName, Object min, Object max) {
		return condition.between(fieldName, min, max);
	}

	@Override
	public Query contains(String fieldName, Object value) {
		return condition.contains(fieldName, value);
	}

	@Override
	public Query nil(String fieldName) {
		return condition.nil(fieldName);
	}

	@Override
	public Query notNil(String fieldName) {
		return condition.notNil(fieldName);
	}

	@Override
	public Query customize(String condition, Object... args) {
		return this.condition.customize(condition, args);
	}

	@Override
	public Object[] args() {
		return condition.args();
	}

	@Override
	public Query asc(String... fields) {
		return order.asc(fields);
	}

	@Override
	public Query asc(boolean nullsFirst, String... fields) {
		return order.asc(nullsFirst, fields);
	}

	@Override
	public Query desc(String... fields) {
		return order.asc(fields);
	}

	@Override
	public Query desc(boolean nullsFirst, String... fields) {
		return order.desc(nullsFirst, fields);
	}

	@Override
	public Query order(String alias, Map<String, ?> orderMap) {
		return order.order(alias, orderMap);
	}

	@Override
	public Query order(Map<String, ?> orderMap) {
		return order.order(orderMap);
	}

	@Override
	public Query group(String... field) {
		return group.group(field);
	}

	/**
	 * Handle the execution in {@code QueryExecutor} in chain invocation.
	 * 
	 * @param executor
	 * @return
	 */
	public <N> N query(QueryExecutor<N> executor) {
		return executor.excute(this.buildRecordQuery(), this.buildCountQuery(),
				this.args());
	}

	/**
	 * Build the SQL to query the records.
	 * 
	 * @return
	 */
	public String buildRecordQuery() {
		String sql = new StringBuilder(select.buildSql())
				.append(condition.buildSql()).append(group.buildSql())
				.append(order.buildSql()).toString().toUpperCase();
		LOG.debug("RECORD QUERY: " + sql);
		return sql;
	}

	/**
	 * Build the SQL to query the count of records. This method will create a
	 * fragment of SQL with {@code "select count(*)"}. <br>
	 * 
	 * <b>Note</b>: If any {@code using} is defined in query, this method will
	 * cause ambiguity exception.
	 * 
	 * @return
	 */
	public String buildCountQuery() {
		String sql = new StringBuilder(AbstractSelect.COUNT_SELECT)
				.append(select.buildFrom()).append(condition.buildSql())
				.append(group.buildSql()).toString().toUpperCase();
		LOG.debug("COUNT QUERY: " + sql);
		return sql;

	}

	public OracleQuery asOracle() {
		return (OracleQuery) this;
	}
}
