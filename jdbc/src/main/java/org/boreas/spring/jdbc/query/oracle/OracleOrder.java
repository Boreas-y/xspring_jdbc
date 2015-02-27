package org.boreas.spring.jdbc.query.oracle;

import java.util.List;

import org.boreas.spring.jdbc.query.AbstractOrder;
import org.boreas.spring.jdbc.query.Query;

public class OracleOrder extends AbstractOrder {

	public OracleOrder(final Query query) {
		super(query, null);
	}

	public OracleOrder(final Query query, List<String> defaultOrders) {
		super(query, defaultOrders);
	}

	/**
	 * Oracle regards {@code null} as the maximum. If {@code nullFirst} is
	 * {@code false}, add {@code nulls last} after {@code desc}. <br>
	 * <b>eg.</b><br>
	 * <code>
	 * desc(true, id); // order by id desc<br>
	 * desc(false, id); // order by id desc nulls last
	 * </code>
	 */
	@Override
	public Query desc(boolean nullsFirst, String... fields) {
		String strategy = nullsFirst ? " desc" : " desc nulls last";
		for (String field : fields) {
			orders.add(field + strategy);
		}
		return query;
	}

	/**
	 * Oracle regards {@code null} as the maximum. If {@code nullFirst} is
	 * {@code true}, add {@code nulls first} after {@code asc}. <br>
	 * <b>eg.</b><br>
	 * <code>
	 * asc(true, id); // order by id nulls first<br>
	 * asc(false, id); // order by id
	 * </code>
	 */
	@Override
	public Query asc(boolean nullsFirst, String... fields) {
		String strategy = nullsFirst ? " nulls first" : "";
		for (String field : fields) {
			orders.add(field + strategy);
		}
		return query;
	}

}
