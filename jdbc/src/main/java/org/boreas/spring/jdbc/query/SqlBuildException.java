package org.boreas.spring.jdbc.query;

/**
 * Base exception during building a {@code Query}.
 * 
 * @author boreas
 *
 */
public class SqlBuildException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 337184505742860749L;

	public SqlBuildException() {
		super();
	}

	public SqlBuildException(String message) {
		super(message);
	}

	SqlBuildException(String message, Throwable cause) {
		super(message, cause);
	}
}
