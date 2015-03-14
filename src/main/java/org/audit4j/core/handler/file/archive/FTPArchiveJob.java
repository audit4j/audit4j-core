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

package org.audit4j.core.handler.file.archive;

import java.net.UnknownHostException;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.exception.Audit4jRuntimeException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.util.annotation.Beeta;

/**
 * The Class FTPArchiveJob.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@Beeta
public class FTPArchiveJob extends AbstractArchiveJob {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8651767095397282923L;
  
    /** The client arguments. */
    String[] clientArguments;

    /* (non-Javadoc)
     * @see org.audit4j.core.handler.file.ArchiveJob#archive()
     */
    @Override
    void archive() {
        FTPArchiveClient client = new FTPArchiveClient();
        try {
            client.init(clientArguments);
        } catch (UnknownHostException e) {
            throw new Audit4jRuntimeException("Excepting in running archive client.", e);
        }
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#init()
     */
    @Override
    public void init() throws InitializationException {
        clientArguments = super.archiveOptions.split(CoreConstants.SEMI_COLON);
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#stop()
     */
    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

}
