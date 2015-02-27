package org.boreas.spring.jdbc.query.oracle;

import java.util.Arrays;

import org.boreas.spring.jdbc.config.NameMapper;
import org.boreas.spring.jdbc.query.AbstractGroup;
import org.boreas.spring.jdbc.query.AbstractQueryFactory;
import org.boreas.spring.jdbc.query.Query;

/**
 * Factory class to create {@code OracleQuery} object.
 * 
 * @author boreas
 *
 */
public class OracleQueryFactory extends AbstractQueryFactory {

	public OracleQueryFactory(NameMapper nameMapper) {
		super(nameMapper);
	}

	@Override
	public Query create(String... defaultOrders) {
		OracleQuery query = new OracleQuery();
		new OracleSelect(query, nameMapper);
		new OracleCondition(query);
		new OracleOrder(query, Arrays.asList(defaultOrders));
		new AbstractGroup(query) {
		};
		return query;
	}

}
