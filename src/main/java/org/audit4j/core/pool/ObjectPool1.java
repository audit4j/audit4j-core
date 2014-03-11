package org.audit4j.core.pool;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The Class ObjectPool.
 *
 * @param <T> the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public abstract class ObjectPool1<T> {
    
    /** The expiration time. */
    private final long expirationTime;

    /** The unlocked. */
    private final Hashtable<T, Long> locked, unlocked;

    /**
     * Instantiates a new object pool.
     */
    public ObjectPool1() {
        expirationTime = 30000; // 30 seconds
        locked = new Hashtable<T, Long>();
        unlocked = new Hashtable<T, Long>();
    }

    /**
     * Creates the.
     *
     * @return the t
     */
    protected abstract T create();

    /**
     * Validate.
     *
     * @param o the o
     * @return true, if successful
     */
    public abstract boolean validate(T o);

    /**
     * Expire.
     *
     * @param o the o
     */
    public abstract void expire(T o);

    /**
     * Check out.
     *
     * @return the t
     */
    protected synchronized T checkOut() {
        long now = System.currentTimeMillis();
        T t;
        if (unlocked.size() > 0) {
            Enumeration<T> e = unlocked.keys();
            while (e.hasMoreElements()) {
                t = e.nextElement();
                if ((now - unlocked.get(t)) > expirationTime) {
                    // object has expired
                    System.out.println("expired");
                    unlocked.remove(t);
                    expire(t);
                    t = null;
                } else {
                    if (validate(t)) {
                        unlocked.remove(t);
                        locked.put(t, now);
                        return (t);
                    } else {
                        // object failed validation
                        unlocked.remove(t);
                        expire(t);
                        t = null;
                    }
                }
            }
        }
        // no objects available, create a new one
        t = create();
        locked.put(t, now);
        return (t);
    }

    /**
     * Check in.
     *
     * @param t the t
     */
    protected synchronized void checkIn(T t) {
        locked.remove(t);
        unlocked.put(t, System.currentTimeMillis());
    }
}
