package org.boreas.spring.jdbc.query;

/**
 * Specify behaviors when building {@code select} and {@code from} clause.
 * 
 * @author boreas
 *
 */
public interface Select {
	/**
	 * Add a inner join table into from clause which no columns will be
	 * selected. The given table is just used as a constraint instead of being
	 * part of result set.<br>
	 * <b>eg.</b>
	 * 
	 * <pre>
	 * for table x : id name pid<br>
	 * SQL: select a.* from x a,x b where a.pid=b.id and b.name=?<br>
	 * code: select.select("x","a").check("x","b") // {@code where} clause is omitted
	 * </pre>
	 * 
	 * @param tableName
	 * @param alias
	 * @return
	 */
	Query check(String tableName, String alias);

	/**
	 * Add a inner join table which one or more columns will be selected.
	 * 
	 * @param tableName
	 *            the table to be joined
	 * @param alias
	 *            the table alias
	 * @param fields
	 *            fields to be selected
	 * @return
	 */
	Query select(String tableName, String alias, String... fields);

	/**
	 * Add a inner join table which one or more columns will be selected.
	 * 
	 * @param query
	 *            another query indicates the table to be joined
	 * @param alias
	 *            the table alias
	 * @param fields
	 *            fields to be selected
	 * @return
	 */
	Query select(Query query, String alias, String... fields);

	/**
	 * Add a left outer join table.
	 * 
	 * @param tableName
	 *            the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join left(String tableName, String alias);

	/**
	 * Add a left outer join table.
	 * 
	 * @param query
	 *            another query indicates the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join left(Query query, String alias);

	/**
	 * Add a right outer join table.
	 * 
	 * @param tableName
	 *            the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join right(String tableName, String alias);

	/**
	 * Add a right outer join table.
	 * 
	 * @param query
	 *            another query indicates the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join right(Query query, String alias);

	/**
	 * Add a full outer join table.
	 * 
	 * @param tableName
	 *            the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join full(String tableName, String alias);

	/**
	 * Add a full outer join table.
	 * 
	 * @param query
	 *            another query indicates the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join full(Query query, String alias);

	/**
	 * Add a cross join table.
	 * 
	 * @param tableName
	 *            the table to be joined
	 * @param alias
	 *            the table alias
	 * @param fields
	 * @return
	 */
	Join cross(String tableName, String alias);

	/**
	 * Add a cross join table.
	 * 
	 * @param query
	 *            another query indicates the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join cross(Query query, String alias);

	/**
	 * Add a natural join table.
	 * 
	 * @param tableName
	 *            the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join natural(String tableName, String alias);

	/**
	 * Add a natural join table.
	 * 
	 * @param query
	 *            another query indicates the table to be joined
	 * @param alias
	 *            the table alias
	 * @return
	 */
	Join natural(Query query, String alias);
}
