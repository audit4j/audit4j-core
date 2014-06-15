package org.audit4j.core;

/**
 * The Class DummyMetaData.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class DummyMetaData implements MetaData{

	/* (non-Javadoc)
	 * @see org.audit4j.core.MetaData#getActor()
	 */
	@Override
	public String getActor() {
		return CoreConstants.DEFAULT_ACTOR;
	}

}
