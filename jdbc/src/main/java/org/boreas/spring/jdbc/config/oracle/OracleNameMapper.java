package org.boreas.spring.jdbc.config.oracle;

import org.boreas.spring.jdbc.config.NameMapper;

/**
 * This interface is to map POJO class type to Oracle database attribute for
 * simplifying the configuration of DAO by convention.
 * 
 * @author boreas
 *
 */
public interface OracleNameMapper extends NameMapper {

	/**
	 * Generate the name of sequence by POJO class type.
	 * 
	 * @param type
	 *            POJO class type
	 * @return
	 */
	String buildSequenceName(Class<?> type);

	/**
	 * Generate the name of sequence by given name.
	 * 
	 * @param name
	 * @return
	 */
	String buildSequenceName(String name);
}
