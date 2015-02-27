package org.boreas.spring.jdbc.query;

import org.boreas.spring.jdbc.config.NameMapper;

public abstract class AbstractQueryFactory implements QueryFactory {

	protected final NameMapper nameMapper;

	public AbstractQueryFactory(NameMapper nameMapper) {
		this.nameMapper = nameMapper;
	}

	public NameMapper getNameMapper() {
		return nameMapper;
	}

}
