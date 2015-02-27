package org.boreas.spring.jdbc.dao;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.boreas.spring.util.StringUtils;

/**
 * Hold some common SQL string templates invoked by
 * {@link String#format(String, Object...)}.
 * 
 * @author boreas
 *
 */
public abstract class SimpleSqlFormat {
	private static final String SELECT_ALL_FORMAT = "select t.* from %s t";
	private static final String SELETE_BY_ID_FORMAT = SELECT_ALL_FORMAT
			+ " where t.%s=?";
	private static final String INSERT_FORMAT = "insert into %s (%s) values(%s)";
	private static final String DELETE_BY_ID_FORMAT = "delete from %s where %s=?";

	/**
	 * Generate SQL that select the full table. name.
	 * 
	 * @param tableName
	 * @return
	 */
	public static String selectAll(String tableName) {
		return String.format(SELECT_ALL_FORMAT, tableName);
	}

	/**
	 * Generate SQL that select all columns of the id-determined record of the
	 * given table.
	 * 
	 * @param tableName
	 * @param idName
	 * @return
	 */
	public static String selectAllById(String tableName, String idName) {
		return String.format(SELETE_BY_ID_FORMAT, tableName, idName);
	}

	/**
	 * Generate SQL that insert a record.
	 * 
	 * @param tableName
	 * @param pds
	 *            {@code PropertyDescriptor} array contains all fields need to
	 *            insert.
	 * @return
	 */
	public static String insert(String tableName, PropertyDescriptor[] pds) {
		List<String> columns = new ArrayList<String>(pds.length);
		List<String> values = new ArrayList<String>(pds.length);
		for (PropertyDescriptor pd : pds) {
			columns.add(pd.getName());
			if (Date.class.isAssignableFrom(pd.getPropertyType()))
				values.add("to_date(?, 'yyyy-MM-dd HH24:mi:ss')");
			else
				values.add("?");
		}
		return String.format(INSERT_FORMAT, tableName,
				StringUtils.combine(",", columns),
				StringUtils.combine(",", values));
	}

	public static String deleteById(String tableName, String idName) {
		return String.format(DELETE_BY_ID_FORMAT, tableName, idName);
	}
}
