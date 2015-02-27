package org.boreas.spring.jdbc.query;

/**
 * Interface of factory to create {@code Query}s
 * 
 * @author boreas
 *
 */
public interface QueryFactory {
	/**
	 * Create a query with default orders, which means that the query will use
	 * the default orders if no order rules are defined in query. Default orders
	 * is recommended when using page query because some databases will retrieve
	 * records randomly if no {@code order} clause is defined.
	 * 
	 * @param defaultOrders
	 *            default order strings. eg. {@code "id desc"},
	 *            {@code "id,name desc"}
	 * @return
	 */
	Query create(String... defaultOrders);
}
