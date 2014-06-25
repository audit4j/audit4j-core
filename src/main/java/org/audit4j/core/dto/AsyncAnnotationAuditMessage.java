package org.audit4j.core.dto;

import java.io.Serializable;

import org.audit4j.core.Configuration;

/**
 * The Class AsyncAnnotationAuditMessage.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AsyncAnnotationAuditMessage implements Serializable{

	/**
	 * asdas
	 */
	private static final long serialVersionUID = 1L;

	/** The conf. */
	private Configuration conf;
	
	/** The async audit dto. */
	private AnnotationAuditEvent asyncAuditDto;
	
	/**
	 * Gets the conf.
	 *
	 * @return the conf
	 */
	public Configuration getConf() {
		return conf;
	}
	
	/**
	 * Sets the conf.
	 *
	 * @param conf the new conf
	 */
	public void setConf(Configuration conf) {
		this.conf = conf;
	}
	
	/**
	 * Gets the async audit dto.
	 *
	 * @return the async audit dto
	 */
	public AnnotationAuditEvent getAsyncAuditDto() {
		return asyncAuditDto;
	}
	
	/**
	 * Sets the async audit dto.
	 *
	 * @param asyncAuditDto the new async audit dto
	 */
	public void setAsyncAuditDto(AnnotationAuditEvent asyncAuditDto) {
		this.asyncAuditDto = asyncAuditDto;
	}
}
