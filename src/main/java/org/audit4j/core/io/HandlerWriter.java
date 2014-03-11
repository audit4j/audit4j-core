package org.audit4j.core.io;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Element;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.pool.FuriousHandlerPool;

/**
 * The Class HandlerWriter.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class HandlerWriter implements AuditOutputStream {

    /** The handlers. */
    private List<Class<? extends Handler>> handlers;

    /** The pool. */
    private final FuriousHandlerPool pool;

    /**
     * Instantiates a new handler writer.
     */
    public HandlerWriter() {
        this.pool = FuriousHandlerPool.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent
     * )
     */
    @Override
    public HandlerWriter write(AuditEvent event) {
        System.out.println("pool" + pool);
        for (Class<? extends Handler> handlerClazz : handlers) {
            Handler handler = pool.checkOut(handlerClazz);
            handler.setAuditEvent(event);
            handler.setQuery(buildQuery(event.getActionItems(), event.getAction()));
            handler.handle();
            pool.checkIn(handler);
        }
        return this;
    }

    /**
     * Adds the.
     * 
     * @param handlers
     *            the handlers
     * @return the handler writer
     */
    public HandlerWriter add(List<Class<? extends Handler>> handlers) {
        this.handlers = handlers;
        return this;
    }
    
    /**
     * Adds the.
     * 
     * @param handlers
     *            the handlers
     * @return the handler writer
     */
    public HandlerWriter add(Class<? extends Handler> handler) {
        this.handlers = new ArrayList<Class<? extends Handler>>();
        handlers.add(handler);
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     */
    @Override
    public void close() {
        handlers.clear();
        handlers = null;
    }
    
    /**
     * Builds the query.
     * 
     * @param actionItems
     *            the action items
     * @param action
     *            the action
     * @return the string
     */
    private String buildQuery(final List<Element> actionItems, String action) {
        if (actionItems != null && !actionItems.isEmpty()) {
            final StringBuilder buff = new StringBuilder();
            if (action != null) {
                buff.append(action).append(CoreConstants.ARROW);
            }
            for (Element actionItem : actionItems) {
                buff.append(actionItem.getName()).append(CoreConstants.COLON_CHAR).append(actionItem.getValue())
                        .append(CoreConstants.COMMA_CHAR);
            }
            return buff.toString();
        } else {
            return "No data for selectred audit criteria";
        }
    }
}
