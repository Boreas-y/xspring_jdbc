package org.boreas.spring.jdbc.config.oracle;

import org.boreas.spring.jdbc.config.SimpleNameMapper;

public class SimpleOracleNameMapper extends SimpleNameMapper implements
		OracleNameMapper {

	private String sequencePrefix = "";
	private String sequenceSuffix = "";

	@Override
	public String buildSequenceName(Class<?> type) {
		return new StringBuilder(sequencePrefix).append(type.getSimpleName())
				.append(sequenceSuffix).toString();
	}

	@Override
	public String buildSequenceName(String name) {
		return new StringBuilder(sequencePrefix).append(name)
				.append(sequenceSuffix).toString();
	}

	public String getSequencePrefix() {
		return sequencePrefix;
	}

	public void setSequencePrefix(String sequencePrefix) {
		this.sequencePrefix = sequencePrefix;
	}

	public String getSequenceSuffix() {
		return sequenceSuffix;
	}

	public void setSequenceSuffix(String sequenceSuffix) {
		this.sequenceSuffix = sequenceSuffix;
	}

}
