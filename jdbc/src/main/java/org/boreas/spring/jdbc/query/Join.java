package org.boreas.spring.jdbc.query;

/**
 * Specify behaviors when building clauses with keyword {@code join}.
 * 
 * @author boreas
 *
 */
public interface Join {
	public enum JoinType {
		LEFT, RIGHT, FULL, NATURAL, CROSS
	};

	On on(String... expressions);

	Using using(String... fields);
}
