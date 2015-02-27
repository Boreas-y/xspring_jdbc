package org.boreas.spring.jdbc.config;

public class DaoConfig<T> {

	private Class<T> beanType;
	private String tableName;
	private String idColumn;

	public DaoConfig(Class<T> beanType) {
		this.beanType = beanType;
	}

	public Class<T> getBeanType() {
		return beanType;
	}

	public void setBeanType(Class<T> beanType) {
		this.beanType = beanType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	

}
