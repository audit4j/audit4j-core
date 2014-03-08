/*
 * Copyright 2014 Janith Bandara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.audit4j.intregration.StaticAuditBinder;


/**
 * A factory for creating AuditBinder objects.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditBinderFactory {

	/** The static logger binder path. */
	private static String STATIC_AUDIT_BINDER_PATH = "com/audit4j/intregration/StaticLevelBinder.class";

	/** The Constant UNINITIALIZED. */
	static final int UNINITIALIZED = 0;

	/** The Constant ONGOING_INITIALIZATION. */
	static final int ONGOING_INITIALIZATION = 1;

	/** The Constant FAILED_INITIALIZATION. */
	static final int FAILED_INITIALIZATION = 2;

	/** The Constant SUCCESSFUL_INITIALIZATION. */
	static final int SUCCESSFUL_INITIALIZATION = 3;

	/** The Constant NOP_FALLBACK_INITIALIZATION. */
	static final int NOP_FALLBACK_INITIALIZATION = 4;

	/** The initialization state. */
	static int INITIALIZATION_STATE = UNINITIALIZED;

	/** The default configuration factory. */
	static AbstractConfigurationFactory DEFAULT_CONFIGURATION_FACTORY = new DefaultConfigurationFactory();

	/**
	 * Gets the initialization state.
	 * 
	 * @return the initialization state
	 */
	public int getInitializationState() {
		return INITIALIZATION_STATE;
	}

	/**
	 * Gets the audit binder.
	 * 
	 * @return the audit binder
	 */
	public AbstractConfigurationFactory getAuditBinder() {
		if (INITIALIZATION_STATE == UNINITIALIZED) {
			INITIALIZATION_STATE = ONGOING_INITIALIZATION;
			performInitialization();
		}
		switch (INITIALIZATION_STATE) {
		case SUCCESSFUL_INITIALIZATION:
			return StaticAuditBinder.getSingleton().getConfigurationFactory();
		case NOP_FALLBACK_INITIALIZATION:
			return DEFAULT_CONFIGURATION_FACTORY;
		case FAILED_INITIALIZATION:
			return DEFAULT_CONFIGURATION_FACTORY;
		case ONGOING_INITIALIZATION:
			return DEFAULT_CONFIGURATION_FACTORY;
		}
		throw new IllegalStateException("Unreachable code");
	}

	/**
	 * Perform initialization.
	 */
	private final static void performInitialization() {
		bind();
		// if (INITIALIZATION_STATE == SUCCESSFUL_INITIALIZATION) {
		// versionSanityCheck();
		// }
	}

	/**
	 * Bind.
	 */
	private final static void bind() {
		try {
			Set staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();
			reportMultipleBindingAmbiguity(staticLoggerBinderPathSet);
			// the next line does the binding
			StaticAuditBinder.getSingleton();
			INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION;
			reportActualBinding(staticLoggerBinderPathSet);
			// emitSubstituteLoggerWarning();
		} catch (NoClassDefFoundError ncde) {
			String msg = ncde.getMessage();
			if (messageContainsOrgAuditIntregrationStaticLoggerBinder(msg)) {
				INITIALIZATION_STATE = NOP_FALLBACK_INITIALIZATION;
				AuditUtil.report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
				AuditUtil.report("Defaulting to no-operation (NOP) logger implementation");
				// AuditUtil.report("See " + NO_STATICLOGGERBINDER_URL +
				// " for further details.");
			} else {
				failedBinding(ncde);
				throw ncde;
			}
		} catch (java.lang.NoSuchMethodError nsme) {
			String msg = nsme.getMessage();
			if (msg != null && msg.indexOf("org.slf4j.impl.StaticLoggerBinder.getSingleton()") != -1) {
				INITIALIZATION_STATE = FAILED_INITIALIZATION;
			}
			throw nsme;
		} catch (Exception e) {
			failedBinding(e);
			throw new IllegalStateException("Unexpected initialization failure", e);
		}
	}

	/**
	 * Find possible static logger binder path set.
	 * 
	 * @return the sets the
	 */
	private static Set findPossibleStaticLoggerBinderPathSet() {
		// use Set instead of list in order to deal with bug #138
		// LinkedHashSet appropriate here because it preserves insertion order
		// during iteration
		Set staticLoggerBinderPathSet = new LinkedHashSet();
		try {
			ClassLoader auditLevelFactoryClassLoader = AuditBinderFactory.class.getClassLoader();
			Enumeration paths;
			if (auditLevelFactoryClassLoader == null) {
				paths = ClassLoader.getSystemResources(STATIC_AUDIT_BINDER_PATH);
			} else {
				paths = auditLevelFactoryClassLoader.getResources(STATIC_AUDIT_BINDER_PATH);
			}
			while (paths.hasMoreElements()) {
				URL path = (URL) paths.nextElement();
				staticLoggerBinderPathSet.add(path);
			}
		} catch (IOException ioe) {
			AuditUtil.report("Error getting resources from path", ioe);
		}
		return staticLoggerBinderPathSet;
	}

	/**
	 * Report multiple binding ambiguity.
	 * 
	 * @param staticLoggerBinderPathSet
	 *            the static logger binder path set
	 */
	private static void reportMultipleBindingAmbiguity(Set staticLoggerBinderPathSet) {
		if (isAmbiguousStaticAuditLevelBinderPathSet(staticLoggerBinderPathSet)) {
			AuditUtil.report("Class path contains multiple SLF4J bindings.");
			Iterator iterator = staticLoggerBinderPathSet.iterator();
			while (iterator.hasNext()) {
				URL path = (URL) iterator.next();
				AuditUtil.report("Found binding in [" + path + "]");
			}
			AuditUtil.report("See for an explanation.");
		}
	}

	/**
	 * Checks if is ambiguous static audit level binder path set.
	 * 
	 * @param staticLoggerBinderPathSet
	 *            the static logger binder path set
	 * @return true, if is ambiguous static audit level binder path set
	 */
	private static boolean isAmbiguousStaticAuditLevelBinderPathSet(Set staticLoggerBinderPathSet) {
		return staticLoggerBinderPathSet.size() > 1;
	}

	/**
	 * Report actual binding.
	 * 
	 * @param staticLoggerBinderPathSet
	 *            the static logger binder path set
	 */
	private static void reportActualBinding(Set staticLoggerBinderPathSet) {
		if (isAmbiguousStaticAuditLevelBinderPathSet(staticLoggerBinderPathSet)) {
			AuditUtil.report("Actual binding is of type []");
		}
	}

	/**
	 * Message contains org audit intregration static logger binder.
	 * 
	 * @param msg
	 *            the msg
	 * @return true, if successful
	 */
	private static boolean messageContainsOrgAuditIntregrationStaticLoggerBinder(String msg) {
		if (msg == null)
			return false;
		if (msg.indexOf("com/audit4j/intregration/StaticLevelBinder") != -1)
			return true;
		if (msg.indexOf("com.audit4j.intregration.StaticLevelBinder") != -1)
			return true;
		return false;
	}

	/**
	 * Failed binding.
	 * 
	 * @param t
	 *            the t
	 */
	static void failedBinding(Throwable t) {
		INITIALIZATION_STATE = FAILED_INITIALIZATION;
		AuditUtil.report("Failed to instantiate SLF4J LoggerFactory", t);
	}
}
