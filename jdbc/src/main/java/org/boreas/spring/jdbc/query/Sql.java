package org.boreas.spring.jdbc.query;

/**
 * Specify that a {@code StringBuilder} object contains SQL will be built.
 * 
 * @author boreas
 *
 */
public interface Sql {
	/**
	 * Create a fragment of SQL.
	 * 
	 * @return a {@code StringBuilder} object contains a fragment of SQL.
	 */
	StringBuilder buildSql();
}
