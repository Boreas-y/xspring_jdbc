package org.boreas.spring.jdbc.query;

/**
 * Specify behaviors when building {@code using} clause.
 * 
 * @author boreas
 *
 */
public interface Using extends JoinCondition {

	/**
	 * Define fields that two tables join on.
	 * 
	 * @param fields
	 *            field names without any table alias.
	 * @return
	 */
	Using using(String... fields);
}
