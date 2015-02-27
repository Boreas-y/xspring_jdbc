package org.boreas.spring.jdbc.config;

/**
 * This interface is to map POJO class type to generic database attribute for
 * simplifying the configuration of DAO by convention.
 * 
 * @author boreas
 *
 */
public interface NameMapper {

	/**
	 * Generate the name of table
	 * 
	 * @param tableName
	 *            the name to be decorated
	 * @return
	 */
	String buildTableName(String tableName);

	/**
	 * Generate the name of table by POJO class type.
	 * 
	 * @param type
	 *            POJO class type
	 * @return
	 */
	String buildTableName(Class<?> type);
}
