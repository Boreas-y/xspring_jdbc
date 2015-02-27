package org.boreas.spring.jdbc.query;

/**
 * Specifies base behaviors of {@code join} condition.
 * 
 * @author boreas
 *
 */
public interface JoinCondition {
	/**
	 * Select no columns of the joined table.
	 * 
	 * @return
	 */
	Query nocol();

	/**
	 * Specify the columns that will be selected. All columns will be selected
	 * by using {@code *} if there's no given fields.
	 * 
	 * @param fields
	 * @return
	 */
	Query col(String... fields);
}
