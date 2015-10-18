package org.audit4j.core.Mock;

import org.audit4j.core.MetaData;

public class MetaDataMock implements MetaData{

	/**
	 * asdas
	 */
	private static final long serialVersionUID = 6240763046493240234L;

	@Override
	public String getActor() {
		return "Test Actor";
	}

    @Override
    public String toString() {
        return "TestMetaData [getActor()=" + getActor() + "]";
    }

    @Override
    public String getOrigin() {
        return "Test Origin";
    }
}
