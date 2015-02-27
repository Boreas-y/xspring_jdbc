package org.boreas.spring.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class StringUtils extends org.springframework.util.StringUtils {
	private StringUtils() {
	}

	/**
	 * Combine objects with the specified separator.
	 * 
	 * @param separator
	 * @param objs
	 *            the objects to be combined
	 * @return
	 */
	// @SafeVarargs
	public static <T> String combine(String separator, T... objs) {
		return combine(separator, Arrays.asList(objs));
	}

	/**
	 * Combine the objects in {@code Collection} with the specified separator.
	 * 
	 * @param separator
	 * @param objs
	 *            the collection to be combined
	 * @return
	 */
	public static <T> String combine(String separator, Collection<T> objs) {
		StringBuffer sb = new StringBuffer();
		if (objs != null && objs.size() > 0) {
			Iterator<T> it = objs.iterator();
			for (;;) {
				sb.append(it.next().toString());
				if (it.hasNext())
					sb.append(separator);
				else
					break;
			}
		}
		return sb.toString();
	}
}
