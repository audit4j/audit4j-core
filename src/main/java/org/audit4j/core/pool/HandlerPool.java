package org.audit4j.core.pool;

import org.audit4j.core.handler.Handler;

/**
 * The Class HandlerPool.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class HandlerPool extends ObjectPool1<Handler> {

    private static HandlerPool pool;

    /** The handler clazz. */
    Class<? extends Handler> handlerClazz;

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.handler.ObjectPool#create()
     */
    @Override
    protected Handler create() {
        try {
            System.out.println("Creating a new HAndler: " + handlerClazz.getName());
            return handlerClazz.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.handler.ObjectPool#validate(java.lang.Object)
     */
    @Override
    public boolean validate(Handler o) {
        System.out.println("validate=" + o.getClass().getName());
        if (o.getClass().getName().equals(handlerClazz.getName())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.handler.ObjectPool#expire(java.lang.Object)
     */
    @Override
    public void expire(Handler o) {
        o.close();
    }

    /**
     * Check out.
     * 
     * @param handlerClazz
     *            the handler clazz
     * @return the handler
     */
    public Handler checkOut(Class<? extends Handler> handlerClazz) {
        System.out.println("Checkout handler: " + handlerClazz.getName());
        this.handlerClazz = handlerClazz;
        return super.checkOut();
    }

    /**
     * Check in.
     * 
     * @param handler
     *            the handler
     */
    @Override
    public void checkIn(Handler handler) {
        System.out.println("Checking handler: " + handlerClazz.getName());
        handlerClazz = null;
        super.checkIn(handler);
    }

    /**
     * Gets the single instance of AuditHelper.
     * 
     * @return single instance of AuditHelper
     */
    public static HandlerPool getInstance() {
        synchronized (HandlerPool.class) {
            if (pool == null) {
                pool = new HandlerPool();
            }
        }
        return pool;
    }
}
