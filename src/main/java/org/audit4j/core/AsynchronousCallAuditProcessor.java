/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
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

package org.audit4j.core;

import org.audit4j.core.dto.AsyncCallAuditDto;
import org.audit4j.core.handler.Handler;


/**
 * The Class AsynchronousCallAuditProcessor.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@Deprecated
public class AsynchronousCallAuditProcessor extends AuditProcessor<AsyncCallAuditDto> {

	/** The audit processor. */
	private static AsynchronousCallAuditProcessor auditProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bi3.commons.audit.AuditProcessor#process(com.bi3.commons.audit.dto
	 * .AuditBaseDto)
	 */
	@Deprecated
	@Override
	public void process(AsyncCallAuditDto auditDto) {
		for (final Handler handler : auditDto.getHandlers()) {
			handler.setQuery(AuditUtil.buildQuery(AuditUtil.transformMap(auditDto.getParamMap()), auditDto.getAction()));
			// handler.setParameters(AuditUtil.transformMap(auditDto.getParamMap()));
			handler.handle();
		}
	}

	/**
	 * Gets the single instance of AuditHelper.
	 * 
	 * @return single instance of AuditHelper
	 */
	public static AsynchronousCallAuditProcessor getInstance() {
		synchronized (AsynchronousCallAuditProcessor.class) {
			if (auditProcessor == null) {
				auditProcessor = new AsynchronousCallAuditProcessor();
			}
		}
		return auditProcessor;
	}

}
