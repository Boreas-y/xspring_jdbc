package org.boreas.spring.jdbc.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.boreas.spring.jdbc.Page;
import org.boreas.spring.jdbc.PageQuery;
import org.boreas.spring.jdbc.config.DaoConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 * Base implementation of {@code SingleColumnIdDao}.
 * 
 * @author boreas
 *
 * @param <ID>
 *            type of id which is only a single column primary key.
 * @param <T>
 *            type of POJO
 */
public abstract class AbstractJdbcDao<ID, T> extends
		NamedParameterJdbcDaoSupport implements SingleColumnIdDao<ID, T> {

	private String selectAllSql;
	private String selectByIdSql;
	private String deleteByIdSql;

	private DaoConfig<T> daoConfig;
	private RowMapper<T> rowMapper;
	/**
	 * Cache all <code>PropertyDescriptor</code>s of the specified bean type.
	 */
	private Map<String, PropertyDescriptor> descriptors;

	@Resource(name = "dataSource")
	public final void setMyDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	public AbstractJdbcDao() {
	}

	protected final void setConfig(DaoConfig<T> daoConfig) {
		this.daoConfig = daoConfig;
		init();
	}

	/**
	 * Initial some common SQL strings with configurations. Create
	 * {@code RowMapper} instance for query.
	 */
	private synchronized void init() {
		Class<T> type = daoConfig.getBeanType();
		createRowMapper(type);
		String tableName = daoConfig.getTableName().toUpperCase();
		daoConfig.setTableName(tableName);
		String idName = daoConfig.getIdColumn().toUpperCase();
		daoConfig.setIdColumn(idName);
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);
		Map<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>(
				pds.length);
		for (PropertyDescriptor pd : pds) {
			// ignore Object.getClass()
			if (!pd.getName().equals("class"))
				descriptors.put(pd.getName().toUpperCase(), pd);
		}
		this.descriptors = Collections.unmodifiableMap(descriptors);
		selectAllSql = SimpleSqlFormat.selectAll(tableName);
		selectByIdSql = SimpleSqlFormat.selectAllById(tableName, idName);
		deleteByIdSql = SimpleSqlFormat.deleteById(tableName, idName);
	}

	/**
	 * Return a {@code DaoConfig} instance to configure DAO.
	 * 
	 * @return
	 */
	protected abstract DaoConfig<T> config();

	/**
	 * Default to create a {@link BeanPropertyRowMapper} instance for
	 * convenience. If high performance is required, it's recommended using a
	 * custom <code>RowMapper</code> without reflection by overriding this
	 * method.
	 * 
	 * @param _class
	 */
	protected void createRowMapper(Class<T> beanType) {
		rowMapper = new BeanPropertyRowMapper<T>(beanType);
	}

	/**
	 * Set the attribute to the given value.
	 * 
	 * @param bean
	 * @param columnName
	 * @param value
	 */
	protected void setValue(T bean, String columnName, Object value) {
		Method setter = descriptors.get(columnName.toUpperCase())
				.getWriteMethod();
		try {
			setter.invoke(bean, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the specified attribute of the given bean.
	 * 
	 * @param bean
	 * @param columnName
	 * @return
	 */
	protected Object getValue(T bean, String columnName) {
		Method getter = descriptors.get(columnName.toUpperCase())
				.getReadMethod();
		try {
			return getter.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public T queryById(ID id) {
		return getJdbcTemplate().queryForObject(selectByIdSql, toObjArr(id),
				rowMapper);
	}

	@Override
	public List<T> query(String sql) {
		return getJdbcTemplate().query(sql, rowMapper);
	}

	@Override
	public List<T> query(String sql, Map<String, ?> args) {
		return getNamedParameterJdbcTemplate().query(sql, args, rowMapper);
	}

	@Override
	public List<T> query(String sql, Object... args) {
		return getJdbcTemplate().query(sql, args, rowMapper);
	}

	@Override
	public Page<T> query(PageQuery pageQuery) {
		Page<T> page = new Page<T>();
		page.setPagination(pageQuery.getPagination());
		page.setPageSize(pageQuery.getPageSize());
		int totalRows = queryTotalRows(pageQuery);
		page.setTotalRows(totalRows);
		int totalPage = (totalRows - 1) / pageQuery.getPageSize() + 1;
		page.setTotalPage(totalPage);
		page.setBeans(getJdbcTemplate().query(buildPagedSQL(pageQuery),
				pageQuery.getArgs(), rowMapper));
		return page;
	}

	@Override
	public List<T> queryAll() {
		return query(selectAllSql);
	}

	@Override
	public void delete(ID id) {
		getJdbcTemplate().update(deleteByIdSql, id);
	}

	// @SafeVarargs
	public final void delete(ID... ids) {
		if (ids.length > 1)
			delete(Arrays.asList(ids));
	}

	@Override
	public void delete(Collection<? extends ID> ids) {
		List<Object[]> batchArgs = new LinkedList<Object[]>();
		for (ID id : ids) {
			batchArgs.add(toObjArr(id));
		}
		getJdbcTemplate().batchUpdate(deleteByIdSql, batchArgs);
	}

	protected int queryTotalRows(PageQuery pageQuery) {
		return getJdbcTemplate().queryForObject(pageQuery.getCountSql(),
				Integer.class, pageQuery.getArgs());
	}

	protected abstract String buildPagedSQL(PageQuery pageQuery);

	/**
	 * Get the descriptors of the bean types.
	 * 
	 * @return
	 */
	protected final Map<String, PropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	protected final RowMapper<T> getRowMapper() {
		return rowMapper;
	}

	protected final DaoConfig<T> getDaoConfig() {
		return daoConfig;
	}

	/**
	 * Convert variable-arity arguments to {@code Object} array.
	 * 
	 * @param objs
	 * @return
	 */
	protected final Object[] toObjArr(Object... objs) {
		return objs;
	}
}
