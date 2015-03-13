package org.boreas.spring.jdbc.query.oracle;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.boreas.spring.jdbc.query.AbstractCondition;
import org.boreas.spring.jdbc.query.Query;

public class OracleCondition extends AbstractCondition {

	// TODO
	private final static Pattern DATE = Pattern
			.compile("^(\\d{4})[\\-](\\d{1,2})[\\-](\\d{1,2})$");
	private final static Pattern DATETIME = Pattern
			.compile("^(\\d{4})[\\-|/|\\.](\\d{1,2})[\\-|/](\\d{1,2}) (\\d{1,2})[\\:](\\d{1,2})[:](\\d{1,2})$");

	// private final static Pattern DATETIME = Pattern.compile(regex)
	public OracleCondition(final Query query) {
		super(query);
	}

	/**
	 * Use Oracle {@code instr} instead of {@code like} for performance.
	 */
	@Override
	public Query contains(String fieldName, Object value) {
		if (!checkNullValue(value)) {
			StringBuilder sql = new StringBuilder("instr(").append(fieldName)
					.append(",?)>0");
			setValue(sql.toString(), value);
		}
		return query;
	}

	@Override
	public Query between(String fieldName, Object min, Object max) {
		Object newMin = min, newMax = max;
		if (min != null && min instanceof String) {
			newMin = tryCovert2Date(min.toString());
		}
		if (max != null) {
			if (Date.class.isAssignableFrom(max.getClass())) {
				newMax = formatEndDate((Date) max);
			} else if (max instanceof String) {
				Date to = tryCovert2Date(max.toString());
				if (to != null)
					newMax = formatEndDate(to);
			}
		}
		// if (min != null && max != null) {
		// if (Date.class.isAssignableFrom(min.getClass())
		// && Date.class.isAssignableFrom(max.getClass())) {
		// return super.between(fieldName, min, formatEndDate((Date) max));
		// } else if (min instanceof String && max instanceof String) {
		// Date from = tryCovert2Date(min.toString());
		// Date to = tryCovert2Date(max.toString());
		// if (from != null && to != null) {
		// return super.between(fieldName, from, formatEndDate(to));
		// }
		// }
		// }
		return super.between(fieldName, newMin, newMax);
	}

	protected Date tryCovert2Date(String str) {
		Matcher dateMathcer = DATE.matcher(str);
		if (dateMathcer.matches()) {
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.valueOf(dateMathcer.group(1)),
					Integer.valueOf(dateMathcer.group(2)) - 1,
					Integer.valueOf(dateMathcer.group(3)), 0, 0, 0);
			return cal.getTime();
		} else {
			Matcher datetimeMatcher = DATETIME.matcher(str);
			if (datetimeMatcher.matches()) {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.valueOf(dateMathcer.group(1)),
						Integer.valueOf(dateMathcer.group(2)) - 1,
						Integer.valueOf(dateMathcer.group(3)),
						Integer.valueOf(dateMathcer.group(4)),
						Integer.valueOf(dateMathcer.group(5)),
						Integer.valueOf(dateMathcer.group(6)));
				return cal.getTime();
			}
		}
		return null;
	}

	/**
	 * If the end date of range doesn't define time, set time 23:59:59.<br>
	 * eg. <br>
	 * 2014-01-01 -> 2014-01-01 23:59:59<br>
	 * 2014-01-01 00:00:00 -> 2014-01-01 23:59:59<br>
	 * 2014-01-01 11:00:00 -> 2014-01-01 11:00:00<br>
	 * 
	 * @param date
	 */
	private Date formatEndDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0
				&& cal.get(Calendar.SECOND) == 0) {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			return cal.getTime();
		}
		return date;
	}

}
