package org.audit4j.core.dto;

import java.io.Serializable;

/**
 * The Class EventMeta.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class EventMeta implements Serializable {

	private static final long serialVersionUID = 5190838195346568817L;

	/** The client. */
	public String client;

	/** The repository. */
	@Deprecated
	public String repository;

	/**
	 * Gets the client.
	 * 
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 * 
	 * @param client
	 *            the new client
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * Gets the repository.
	 * 
	 * @return the repository
	 */
	@Deprecated
	public String getRepository() {
		return repository;
	}

	/**
	 * Sets the repository.
	 * 
	 * @param repository
	 *            the new repository
	 */
	@Deprecated
	public void setRepository(String repository) {
		this.repository = repository;
	}
}
