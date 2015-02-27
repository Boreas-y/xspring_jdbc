package org.boreas.spring.jdbc.query;

/**
 * Specify behaviors when building {@code on} clause.
 * 
 * @author boreas
 *
 */
public interface On extends JoinCondition {
	On on(String... expressions);
}
