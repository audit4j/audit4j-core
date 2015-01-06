package org.audit4j.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;

/**
 * The Class ConcurrentConfigurationContext.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ConcurrentConfigurationContext {

	/** The handlers. */
	private final List<Handler> handlers = new CopyOnWriteArrayList<Handler>();

	/** The properties. */
	private final Map<String, String> properties = new ConcurrentHashMap<String, String>();

	/** The Constant filters. */
	private final List<AuditEventFilter> filters = new CopyOnWriteArrayList<AuditEventFilter>();

	/** The layout. */
	private Layout layout;

	/** The meta data. */
	private MetaData metaData;

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public List<Handler> getHandlers() {
		return handlers;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public List<AuditEventFilter> getFilters() {
		return filters;
	}

	/**
	 * Adds the handler.
	 * 
	 * @param handler
	 *            the handler
	 */
	public void addHandler(Handler handler) {
		handlers.add(handler);
	}

	/**
	 * Adds the property.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * Adds the filter.
	 * 
	 * @param filter
	 *            the filter
	 */
	public void addFilter(AuditEventFilter filter) {
		filters.add(filter);
	}
}
