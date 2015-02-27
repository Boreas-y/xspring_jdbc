package org.boreas.spring.jdbc;

public class JdbcException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5259788158307430687L;

	public JdbcException() {
		super();
	}

	public JdbcException(String message) {
		super(message);
	}

	public JdbcException(Throwable cause) {
		super(cause);
	}

	public JdbcException(String message, Throwable cause) {
		super(message, cause);
	}
}
