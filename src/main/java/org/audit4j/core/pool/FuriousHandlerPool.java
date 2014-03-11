package org.audit4j.core.pool;

import nf.fr.eraasoft.pool.ObjectPool;
import nf.fr.eraasoft.pool.PoolException;
import nf.fr.eraasoft.pool.PoolSettings;
import nf.fr.eraasoft.pool.PoolableObject;
import nf.fr.eraasoft.pool.PoolableObjectBase;

import org.audit4j.core.handler.Handler;

public class FuriousHandlerPool extends Object {

	Class<? extends Handler> handlerClazz;

	ObjectPool<Handler> objectPool;

	private static FuriousHandlerPool pool;
	
	public FuriousHandlerPool() {

		PoolableObject<Handler> poolableObject = new PoolableObjectBase<Handler>() {

			@Override
			public void activate(Handler arg0) throws PoolException {
				// TODO Auto-generated method stub

			}

			@Override
			public Handler make() throws PoolException {
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

			@Override
			public boolean validate(Handler t) {
				System.out.println("validate=" + t.getClass().getName());
				if (t.getClass().getName().equals(handlerClazz.getName())) {
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			}
		};

		PoolSettings<Handler> poolSettings = new PoolSettings<Handler>(poolableObject);
		poolSettings.min(0);

		objectPool = poolSettings.pool();
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
		try {
			return objectPool.getObj();
		} catch (PoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check in.
	 * 
	 * @param handler
	 *            the handler
	 */
	public void checkIn(Handler handler) {
		System.out.println("Checking handler: " + handlerClazz.getName());
		handlerClazz = null;
		objectPool.returnObj(handler);
	}

	/**
	 * Gets the single instance of AuditHelper.
	 * 
	 * @return single instance of AuditHelper
	 */
	public static FuriousHandlerPool getInstance() {
		synchronized (FuriousHandlerPool.class) {
			if (pool == null) {
				pool = new FuriousHandlerPool();
			}
		}
		return pool;
	}
}
