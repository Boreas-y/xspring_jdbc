package org.boreas.spring.jdbc.dao;

import java.util.Collection;

/**
 * This interface is for those tables which have a single column primary key.
 * Some common used actions with id column are defined for convenience.
 * 
 * @author boreas
 *
 * @param <ID>
 *            type of id which is only a single column primary key.
 * @param <T>
 *            type of POJO
 */
public interface SingleColumnIdDao<ID, T> extends GenericDao<T> {

	/**
	 * Query a entity bean by ID.
	 * 
	 * @param id
	 * @return
	 */
	T queryById(ID id);

	/**
	 * Delete an record by ID.
	 * 
	 * @param id
	 */
	void delete(ID id);

	/**
	 * Delete records by IDs in batch mode.
	 * 
	 * @param ids
	 */
	void delete(Collection<? extends ID> ids);

	/**
	 * Delete records by IDs in batch mode. Implementation should be final and
	 * need a <code>@SafeVarargs</code> annotation.
	 * 
	 * @param ids
	 */
	void delete(ID... ids);
}
