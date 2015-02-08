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

import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;

import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.function.Consumer;
import reactor.function.support.Boundary;

/**
 * The Class AsyncAuditOutputStream.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.0.0
 */
public class AsyncAuditOutputStream implements AuditOutputStream {

    /** The output stream. */
    final AuditOutputStream outputStream;

    /** The deferred. */
    Deferred<AuditEvent, Stream<AuditEvent>> deferred = null;

    /** The annotation deferred. */
    Deferred<AnnotationAuditEvent, Stream<AnnotationAuditEvent>> annotationDeferred = null;

    /** The Constant ENV. */
    static final Environment ENV = new Environment();

    /** The b. */
    Boundary b = null;

    /** The b anno. */
    Boundary bAnno = null;

    /**
     * Instantiates a new async audit output stream.
     * 
     * @param outputStream
     *            the output stream
     */
    public AsyncAuditOutputStream(final AuditOutputStream outputStream) {

        this.outputStream = outputStream;
        b = new Boundary();
        deferred = Streams.<AuditEvent> defer().env(ENV).dispatcher(Environment.RING_BUFFER)
                .dispatcher(Environment.WORK_QUEUE).get();
        Stream<AuditEvent> stream = deferred.compose();
        stream.consume(b.bind(new Consumer<AuditEvent>() {
            @Override
            public void accept(AuditEvent event) {
                outputStream.write(event);
            }
        }));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent
     * )
     */
    @Override
    public AsyncAuditOutputStream write(AuditEvent event) {
        deferred.accept(event);
        b.await();
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     */
    @Override
    public void close() {
        ENV.shutdown();
        if (outputStream != null) {
            outputStream.close();
        }
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
