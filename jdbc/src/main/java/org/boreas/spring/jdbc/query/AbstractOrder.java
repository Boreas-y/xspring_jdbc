package org.boreas.spring.jdbc.query;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.boreas.spring.util.StringUtils;
import org.springframework.util.CollectionUtils;

public abstract class AbstractOrder implements Sql, Order {

	private static final String ASC = "asc";
	private static final String DESC = "desc";

	protected final Query query;
	protected final List<String> orders;
	protected final List<String> defaultOrders;

	public AbstractOrder(final Query query, List<String> defaultOrders) {
		this.query = query;
		this.orders = new LinkedList<String>();
		this.defaultOrders = CollectionUtils.isEmpty(defaultOrders) ? new LinkedList<String>()
				: defaultOrders;
		query.order = this;
	}

	@Override
	public Query asc(String... fields) {
		for (String field : fields) {
			if (!StringUtils.hasText(field))
				orders.add(field);
		}
		return query;
	}

	@Override
	public Query desc(String... fields) {
		for (String field : fields) {
			if (!StringUtils.hasText(field))
				orders.add(field + " desc");
		}
		return query;
	}

	@Override
	public Query order(String alias, Map<String, ?> orderMap) {
		String tableAlias = StringUtils.hasText(alias) ? alias + "." : "";
		for (String key : orderMap.keySet()) {
			StringBuilder sql = new StringBuilder(tableAlias).append(key);
			if (isAsc(orderMap.get(key)))
				orders.add(sql.toString());
			else
				orders.add(sql.append(" desc").toString());
		}
		return query;
	}

	@Override
	public Query order(Map<String, ?> orderMap) {
		return order("", orderMap);
	}

	/**
	 * Build the {@code order} clause. Use {@code orders} if there're order
	 * rules defined. If no {@code orders} but {@code defaultOrders} is defined,
	 * use {@code defaultOrders}. If both of them are not defined, return empty
	 * {@code StringBuilder}.
	 */
	@Override
	public StringBuilder buildSql() {
		StringBuilder sql = new StringBuilder();
		if (orders.isEmpty()) {
			if (!defaultOrders.isEmpty()) {
				sql.append(" order by ").append(
						StringUtils.combine(",", defaultOrders));
			}
		} else
			sql.append(" order by ").append(StringUtils.combine(",", orders));
		return sql;
	}

	protected boolean isAsc(Object value) {
		boolean isAsc = true;
		if (Number.class.isAssignableFrom(value.getClass())) {
			if (((Number) value).doubleValue() <= 0)
				isAsc = false;
		} else if (value instanceof Boolean) {
			if (Boolean.FALSE.equals(value))
				isAsc = false;
		} else {
			String str = value.toString();
			if (DESC.equalsIgnoreCase(str)) {
				isAsc = false;
			} else if (!ASC.equalsIgnoreCase(str))
				throw new SqlBuildException("Invalid order strategy: " + str);
		}
		return isAsc;
	}
}
