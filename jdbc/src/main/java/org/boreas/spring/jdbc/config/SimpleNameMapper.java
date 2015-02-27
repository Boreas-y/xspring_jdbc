package org.boreas.spring.jdbc.config;

/**
 * Simple implementation of {@code NameMapper}, decorating table name with
 * prefix and suffix.
 * 
 * @author boreas
 *
 */
public class SimpleNameMapper implements NameMapper {
	private String tablePrefix = "";
	private String tableSuffix = "";

	@Override
	public String buildTableName(String tableName) {
		return new StringBuilder(tablePrefix).append(tableName)
				.append(tableSuffix).toString();
	}

	@Override
	public String buildTableName(Class<?> type) {
		return buildTableName(type.getSimpleName());
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getTableSuffix() {
		return tableSuffix;
	}

	public void setTableSuffix(String tableSuffix) {
		this.tableSuffix = tableSuffix;
	}

}
