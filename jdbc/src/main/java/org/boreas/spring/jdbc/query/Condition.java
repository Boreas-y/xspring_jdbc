package org.boreas.spring.jdbc.query;

/**
 * Specify behaviors when building {@code where} clause.
 * 
 * @author boreas
 *
 */
public interface Condition {
	/**
	 * Add a condition that indicate the specified field is equal to the given
	 * value if the value is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            value of condition
	 * @return
	 */
	Query eq(String fieldName, Object value);

	/**
	 * Add a condition that indicate the specified field is not equal to the
	 * given value if the value is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            value of condition
	 * @return
	 */
	Query ne(String fieldName, Object value);

	/**
	 * Add a condition that indicate the specified field is less than the given
	 * value if the value is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            value of condition
	 * @return
	 */
	Query lt(String fieldName, Object value);

	/**
	 * Add a condition that indicate the specified field is greater than the
	 * given value if the value is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            value of condition
	 * @return
	 */
	Query gt(String fieldName, Object value);

	/**
	 * Add a condition that indicate the specified field is less or equal to the
	 * given value if the value is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            value of condition
	 * @return
	 */
	Query le(String fieldName, Object value);

	/**
	 * Add a condition that indicate the specified field is greater or equal to
	 * the given value if the value is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            value of condition
	 * @return
	 */
	Query ge(String fieldName, Object value);

	/**
	 * Add a condition that indicate a <b>closed interval</b> if minimum and
	 * maximum are not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param min
	 *            the minimum of interval.
	 * @param max
	 *            the maximum of interval.
	 * @return
	 */
	Query between(String fieldName, Object min, Object max);

	/**
	 * Add a condition that indicate the specified field contains the string if
	 * the string is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @param value
	 *            string which the specified field need to contain.
	 * @return
	 */
	Query contains(String fieldName, Object value);

	/**
	 * Add a condition that indicate the specified field is null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @return
	 */
	Query nil(String fieldName);

	/**
	 * Add a condition that indicate the specified field is not null.
	 * 
	 * @param fieldName
	 *            the constrained field name
	 * @return
	 */
	Query notNil(String fieldName);

	/**
	 * Add a custom condition defined by a fragment of SQL.
	 * 
	 * @param condition
	 *            SQL, eg. <code>id=?</code>
	 * @param args
	 *            arguments in condition.
	 * @return
	 */
	Query customize(String condition, Object... args);

	/**
	 * Get all arguments defined in conditions.
	 * 
	 * @return
	 */
	Object[] args();

}
