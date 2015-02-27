package org.boreas.spring.jdbc.config.oracle;

import org.boreas.spring.jdbc.config.DaoConfig;


public class OracleDaoConfig<T> extends DaoConfig<T> {
	public OracleDaoConfig(Class<T> beanType) {
		super(beanType);
	}

	private String sequenceName;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
}
