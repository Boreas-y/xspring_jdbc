package org.boreas.spring.jdbc.query;

import java.util.Map;

/**
 * Specify behaviors when building {@code order} clause.
 * 
 * @author boreas
 *
 */
public interface Order {
	/**
	 * Define fields which records are sorted by in ascending order.
	 * 
	 * @return
	 */
	Query asc(String... fields);

	/**
	 * Define fields which records are sorted by in ascending order. Redefine
	 * the strategy of {@code null}.
	 * 
	 * @param nullsFirst
	 *            if {@code true}, records with {@code null} will be at the
	 *            start of record set while being at the end of record set if
	 *            {@code false}.
	 * @param fields
	 * @return
	 */
	Query asc(boolean nullsFirst, String... fields);

	/**
	 * Define fields which records are sorted by in descending order.
	 * 
	 * @return
	 */
	Query desc(String... fields);

	/**
	 * Define fields which records are sorted by in descending order. Redefine
	 * the strategy of {@code null}.
	 * 
	 * @param nullsFirst
	 *            if {@code true}, records with {@code null} will be at the
	 *            start of record set while being at the end of record set if
	 *            {@code false}.
	 * @param fields
	 * @return
	 */
	Query desc(boolean nullsFirst, String... fields);

	/**
	 * Define the order rules of a single table by map.
	 * 
	 * @param alias
	 *            table alias, will be applied on field names in
	 *            {@code orderMap}
	 * @param orderMap
	 *            define the order rules. Keys indicate the field names without
	 *            table alias. Values can be string "asc"/"desc", number or
	 *            boolean value true/false. "asc"/true and positive number
	 *            indicate ascending order while "desc"/false/0 and negative
	 *            number indicate descending order.
	 * @return
	 */
	Query order(String alias, Map<String, ?> orderMap);

	/**
	 * Define the order rules by map.
	 * 
	 * @param orderMap
	 *            define the order rules. Keys indicate the field names. Values
	 *            can be string "asc"/"desc", number or boolean value
	 *            true/false. "asc"/true and positive number indicate ascending
	 *            order while "desc"/false/0 and negative number indicate
	 *            descending order.
	 * @return
	 */
	Query order(Map<String, ?> orderMap);
}
