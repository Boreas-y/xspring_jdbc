package org.boreas.spring.jdbc.query.executor;

/**
 * Enable {@code Query} to do something in chain invocation.
 * 
 * @author boreas
 *
 * @param <T>
 */
public interface QueryExecutor<T> {

	/**
	 * @param recordSQL
	 *            SQL to query records.
	 * @param countSQL
	 *            SQL to query count of rows.
	 * @param args
	 *            arguments in SQL
	 * @return
	 */
	T excute(String recordSQL, String countSQL, Object[] args);
}
