package org.boreas.spring.jdbc.query.oracle;

import org.boreas.spring.jdbc.query.Query;

public class OracleQuery extends Query {

	public OracleQuery hint(String hint) {
		return ((OracleSelect) select).hint(hint);
	}
}
