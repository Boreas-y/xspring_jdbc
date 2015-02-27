package org.boreas.spring.jdbc.query;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.boreas.spring.jdbc.config.NameMapper;
import org.boreas.spring.jdbc.query.Join.JoinType;
import org.boreas.spring.util.StringUtils;

/**
 * Base implementation of {@code Select}, implements universal methods.
 * 
 * @author boreas
 *
 */
public abstract class AbstractSelect implements Sql, Select {

	public static final String COUNT_SELECT = "SELECT COUNT(1)";

	private final NameMapper nameMapper;

	protected final Query query;

	/**
	 * Fields in {@code select} clause.
	 */
	private List<String> selectFields = new LinkedList<String>();
	/**
	 * Map of tables using inner join.
	 */
	private Map<String, String> innerJoin = new LinkedHashMap<String, String>();
	/**
	 * Map of tables using other joins.
	 */
	private Map<String, JoinImpl> join = new LinkedHashMap<String, JoinImpl>();

	public AbstractSelect(final Query query, final NameMapper nameMapper) {
		this.nameMapper = nameMapper;
		this.query = query;
		query.select = this;
	}

	@Override
	public Query check(String tableName, String alias) {
		innerJoin.put(alias, nameMapper.buildTableName(tableName));
		return query;
	}

	@Override
	public Query select(String tableName, String alias, String... fields) {
		innerJoin.put(alias, nameMapper.buildTableName(tableName));
		addSelectFields(alias, fields);
		return query;
	}

	@Override
	public Query select(Query query, String alias, String... fields) {
		innerJoin.put(
				alias,
				new StringBuilder("(").append(query.buildRecordQuery())
						.append(')').toString());
		addSelectFields(alias, fields);
		return query;
	}

	@Override
	public Join left(String tableName, String alias) {
		return new JoinImpl(this, tableName, alias, JoinType.LEFT);
	}

	@Override
	public Join left(Query query, String alias) {
		return new JoinImpl(this, query, alias, JoinType.LEFT);
	}

	@Override
	public Join right(String tableName, String alias) {
		return new JoinImpl(this, tableName, alias, JoinType.RIGHT);
	}

	@Override
	public Join right(Query query, String alias) {
		return new JoinImpl(this, query, alias, JoinType.RIGHT);
	}

	@Override
	public Join full(String tableName, String alias) {
		return new JoinImpl(this, tableName, alias, JoinType.FULL);
	}

	@Override
	public Join full(Query query, String alias) {
		return new JoinImpl(this, query, alias, JoinType.FULL);
	}

	@Override
	public Join cross(String tableName, String alias) {
		return new JoinImpl(this, tableName, alias, JoinType.CROSS);
	}

	@Override
	public Join cross(Query query, String alias) {
		return new JoinImpl(this, query, alias, JoinType.CROSS);
	}

	@Override
	public Join natural(String tableName, String alias) {
		return new JoinImpl(this, tableName, alias, JoinType.NATURAL);
	}

	@Override
	public Join natural(Query query, String alias) {
		return new JoinImpl(this, query, alias, JoinType.NATURAL);
	}

	public StringBuilder buildFrom() {
		StringBuilder sql = new StringBuilder(" FROM ");
		Iterator<String> innerJoin = getInnerJoin().keySet().iterator();
		if (innerJoin.hasNext()) {
			for (;;) {
				String alias = innerJoin.next();
				sql.append(getInnerJoin().get(alias)).append(' ').append(alias);
				if (innerJoin.hasNext())
					sql.append(',');
				else
					break;
			}
		} else
			throw new SqlBuildException("Must select 1 table at least.");
		Iterator<String> joins = getJoin().keySet().iterator();
		while (joins.hasNext()) {
			String alias = joins.next();
			JoinImpl join = getJoin().get(alias);
			sql.append(join.getJoinType()).append(join.getTableName())
					.append(' ').append(join.getAlias())
					.append(join.getCondition());
		}
		return sql;
	}

	/**
	 * Add fields into select list. If no field is specified, all columns will
	 * be selected.<br>
	 * <b>Example</b>: <br>
	 * simple field: <code>addSelectFields("t","id"); // select t.id</code><br>
	 * field with alias:
	 * <code>addSelectFields("t","username name"); //select t.username name</code>
	 * </br>function:
	 * <code>addSelectFields("t","count(t.id)"); //select count(t.id)</code>
	 * </br>
	 * 
	 * @param alias
	 *            the table alias
	 * @param fields
	 *            the names of fields or aggregate functions to be selected
	 */
	protected void addSelectFields(String alias, String... fields) {
		if (fields.length == 0)
			selectFields.add(alias + ".*");
		else
			for (String field : fields)
				selectFields.add(formatField(alias, field));
	}

	protected List<String> getSelectFields() {
		return selectFields;
	}

	protected Map<String, String> getInnerJoin() {
		return innerJoin;
	}

	protected Map<String, JoinImpl> getJoin() {
		return join;
	}

	public static class JoinImpl implements Join {
		final AbstractSelect select;
		final String tableName;
		final String alias;
		final List<String> usingList;
		final List<String> onList;
		final JoinType joinType;
		Object[] args = null;

		public JoinImpl(final AbstractSelect select, String tableName,
				String alias, JoinType joinType) {
			this.select = select;
			this.tableName = select.nameMapper.buildTableName(tableName);
			this.alias = alias;
			this.joinType = joinType;
			if (joinType != JoinType.CROSS) {
				this.usingList = new LinkedList<String>();
				this.onList = new LinkedList<String>();
			} else
				this.usingList = this.onList = null;
		}

		public JoinImpl(final AbstractSelect select, Query query, String alias,
				JoinType joinType) {
			this.select = select;
			this.tableName = new StringBuilder("(")
					.append(query.buildRecordQuery()).append(")").toString();
			this.alias = alias;
			this.joinType = joinType;
			if (joinType != JoinType.CROSS) {
				this.usingList = new LinkedList<String>();
				this.onList = new LinkedList<String>();
			} else
				this.usingList = this.onList = null;
			args = query.args();
		}

		@Override
		public On on(String... expressions) {
			if (joinType == JoinType.CROSS)
				throw new SqlBuildException(
						"Cross join should not have using clause.");
			OnBuilder onBuilder = new OnBuilder(this, args);
			onBuilder.on(expressions);
			return onBuilder;
		}

		/**
		 * Add {@code using} constraint.
		 * 
		 * @param fields
		 *            fields that using in table join.
		 * @return
		 */
		@Override
		public Using using(String... fields) {
			if (joinType == JoinType.CROSS)
				throw new SqlBuildException(
						"Cross join should not have using clause.");
			UsingBuilder usingBuilder = new UsingBuilder(this, args);
			usingBuilder.using(fields);
			return usingBuilder;
		}

		public String getTableName() {
			return tableName;
		}

		public String getAlias() {
			return alias;
		}

		public StringBuilder getCondition() {
			StringBuilder condition = new StringBuilder();
			if (joinType != JoinType.CROSS) {
				if (!usingList.isEmpty())
					condition.append(" USING (")
							.append(StringUtils.combine(",", usingList))
							.append(')');
				else if (!onList.isEmpty())
					condition.append(" ON (")
							.append(StringUtils.combine(" and ", onList))
							.append(')');
			}
			return condition;
		}

		/**
		 * Return the join type surrounded with 2 blanks, expectedly used in
		 * build SQL.
		 * 
		 * @return
		 */
		public String getJoinType() {
			switch (joinType) {
			case FULL:
				return " FULL JOIN ";
			case RIGHT:
				return " RIGHT JOIN ";
			case CROSS:
				return " CROSS JOIN ";
			case NATURAL:
				if (usingList.isEmpty() && onList.isEmpty())
					return " NATURAL JOIN ";
				else
					return " JOIN ";
			case LEFT:
			default:
				return " LEFT JOIN ";
			}
		}
	}

	static class JoinConditionImpl implements JoinCondition {
		final JoinImpl join;
		final Object[] args;

		public JoinConditionImpl(JoinImpl join) {
			this.join = join;
			args = null;
		}

		public JoinConditionImpl(JoinImpl join, Object[] args) {
			this.join = join;
			this.args = args;
		}

		/**
		 * Specify that no column will be selected.
		 * 
		 * @return
		 */
		@Override
		public Query nocol() {
			join.select.join.put(join.alias, join);
			if (args != null && args.length > 0)
				join.select.query.condition.addArgs(args);
			return join.select.query;
		}

		/**
		 * Specify the columns that will be selected. All columns will be
		 * selected by using {@code *} if there's no given fields.
		 * 
		 * @param fields
		 * @return
		 */
		@Override
		public Query col(String... fields) {
			List<String> selectList = join.select.selectFields;
			if (fields.length == 0)
				selectList.add(join.alias + ".*");
			else
				for (String field : fields) {
					selectList.add(formatField(join.alias, field));
				}
			join.select.join.put(join.alias, join);
			if (args != null && args.length > 0)
				join.select.query.condition.addArgs(args);
			return join.select.query;
		}
	}

	public static class UsingBuilder extends JoinConditionImpl implements Using {
		public UsingBuilder(JoinImpl join) {
			super(join);
		}

		public UsingBuilder(JoinImpl join, Object[] args) {
			super(join, args);
		}

		public Using using(String... fields) {
			join.usingList.addAll(Arrays.asList(fields));
			return this;
		}
	}

	public static class OnBuilder extends JoinConditionImpl implements On {
		final List<String> expressions = new LinkedList<String>();

		public OnBuilder(JoinImpl join) {
			super(join);
		}

		public OnBuilder(JoinImpl join, Object[] args) {
			super(join, args);
		}

		public OnBuilder on(String... equalExpressions) {
			join.onList.addAll(Arrays.asList(equalExpressions));
			return this;
		}
	}

	/**
	 * Format the field with table alias
	 * 
	 * @param alias
	 *            table alias
	 * @param field
	 *            field name
	 * @return
	 */
	private static String formatField(String alias, String field) {
		String formatName = null;
		if (field.contains("(")) {// function
			formatName = field;
		} else
			formatName = alias + "." + field;
		return formatName;
	}

}
