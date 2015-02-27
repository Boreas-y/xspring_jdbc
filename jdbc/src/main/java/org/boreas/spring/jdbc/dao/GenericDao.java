package org.boreas.spring.jdbc.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.boreas.spring.jdbc.Page;
import org.boreas.spring.jdbc.PageQuery;

/**
 * This interface defines some common used actions for convenience.
 * 
 * @author boreas
 *
 * @param <T>
 *            type of POJO
 */
public interface GenericDao<T> {

	/**
	 * Query a list of beans specified with the given type.
	 * 
	 * @param sql
	 *            SQL string with no arguments.
	 * @return a list of bean
	 */
	List<T> query(String sql);

	/**
	 * Query a list of beans specified with the given type.
	 * 
	 * @param sql
	 *            SQL string with named parameter placeholder, such as
	 *            <code>select name from user where id=:id</code>
	 * @param args
	 *            arguments to fill in SQL
	 * @return a list of bean
	 */
	List<T> query(String sql, Map<String, ?> args);

	/**
	 * Query a list of beans specified with the given type.
	 * 
	 * @param sql
	 *            SQL string with "?" placeholder, such as
	 *            <code>select name from user where id=?</code>
	 * @param args
	 *            arguments to fill in SQL
	 * @return a list of bean
	 */
	List<T> query(String sql, Object... args);

	Page<T> query(PageQuery pageQuery);

	/**
	 * Query all records of a table.
	 * 
	 * @return
	 */
	List<T> queryAll();

	/**
	 * Insert a new record with the data of bean.
	 * 
	 * @param bean
	 */
	void insert(T bean);

	/**
	 * Insert records in batch mode.
	 * 
	 * @param beans
	 */
	void insert(Collection<T> beans);
}
