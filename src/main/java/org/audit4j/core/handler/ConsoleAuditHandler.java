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

package org.audit4j.core.handler;

import java.io.PrintStream;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.core.exception.InitializationException;

/**
 * The Class ConsoleAuditHandler.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ConsoleAuditHandler extends Handler {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4570535029942402303L;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#handle()
	 */
	@Override
	public void handle() {
		final String logText = getQuery();
		PrintStream stream = System.out;
		stream.println(logText);
	}

	/* (non-Javadoc)
	 * @see org.audit4j.core.Initializable#init()
	 */
	@Override
	public void init() throws InitializationException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.audit4j.core.Initializable#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void handle(String formattedEvent) throws HandlerException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handle(AuditEvent event) throws HandlerException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handle(EventBatch batch) throws HandlerException {
        // TODO Auto-generated method stub
        
    }
}
