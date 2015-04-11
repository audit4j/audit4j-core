/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
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

package org.audit4j.core.io;

import org.audit4j.core.AuditEventProcessor;
import org.audit4j.core.AuditProcessor;
import org.audit4j.core.ConcurrentConfigurationContext;
import org.audit4j.core.LifeCycleContext;
import org.audit4j.core.RunStatus;
import org.audit4j.core.dto.AuditEvent;

/**
 * The Class AuditProcessOutputStream.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditProcessOutputStream implements AuditOutputStream {

	/** The config. */
	private final ConcurrentConfigurationContext configContext;

	/** The processor. */
	private final AuditProcessor<AuditEvent> processor;

	/**
	 * Instantiates a new audit process output stream.
	 *
	 * @param configContext the config context
	 */
	public AuditProcessOutputStream(ConcurrentConfigurationContext configContext) {
		this.configContext = configContext;
		processor = AuditEventProcessor.getInstance();
		processor.setConfigContext(configContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent
	 * )
	 */
	@Override
	public AuditProcessOutputStream write(AuditEvent event) {
		if (LifeCycleContext.getInstance().getStatus().equals(RunStatus.RUNNING)) {
			processor.process(event);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.audit4j.core.io.AuditOutputStream#close()
	 */
	@Override
	public void close() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		return null;
	}
}
