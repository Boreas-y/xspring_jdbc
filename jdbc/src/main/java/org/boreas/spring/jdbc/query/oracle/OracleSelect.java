package org.boreas.spring.jdbc.query.oracle;

import org.boreas.spring.jdbc.config.NameMapper;
import org.boreas.spring.jdbc.query.AbstractSelect;
import org.boreas.spring.jdbc.query.Query;
import org.boreas.spring.util.StringUtils;

/**
 * Implementation of {@code Select} for Oracle, offer a method to add Oracle
 * hint.
 * 
 * @author boreas
 *
 */
public class OracleSelect extends AbstractSelect {

	private final static String HINT = "/*+ $ */";
	private String hint;

	public OracleSelect(final Query query, final NameMapper nameMapper) {
		super(query, nameMapper);
	}

	/**
	 * Add a Oracle hint into SQL.
	 * 
	 * @param hint
	 *            the hint string, eg. all_rows
	 * @return
	 */
	public OracleQuery hint(String hint) {
		this.hint = hint;
		return (OracleQuery) query;
	}

	@Override
	public StringBuilder buildSql() {
		StringBuilder sql = new StringBuilder("SELECT ");
		if (!StringUtils.isEmpty(hint))
			sql.append(HINT.replace("$", hint)).append(' ');
		sql.append(StringUtils.combine(",", getSelectFields())).append(
				buildFrom());
		return sql;
	}
}
