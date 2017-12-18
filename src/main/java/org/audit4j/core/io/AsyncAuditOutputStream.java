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
import org.audit4j.core.dto.EventBatch;

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
public class AsyncAuditOutputStream implements AuditOutputStream<AuditEvent> {

    /** The output stream. */
    AuditOutputStream<AuditEvent> outputStream;
    
    /** The annotation stream. */
    AuditOutputStream<AnnotationAuditEvent> annotationStream;

    /** The deferred. */
    Deferred<AuditEvent, Stream<AuditEvent>> deferred = null;
    
    Deferred<EventBatch, Stream<EventBatch>> batchDeferred = null;

    /** The annotation deferred. */
    Deferred<AnnotationAuditEvent, Stream<AnnotationAuditEvent>> annotationDeferred = null;

    /** The Constant ENV. */
    static Environment ENV;

    /** The b. */
    Boundary b = null;

    /** The b anno. */
    Boundary batchBoundry = null;

    /**
     * Instantiates a new async audit output stream.
     *
     * @param outputStream the output stream
     * @param annotationStream the annotation stream
     */
    public AsyncAuditOutputStream(final AuditOutputStream<AuditEvent> outputStream, final AuditOutputStream<AnnotationAuditEvent> annotationStream) {
        this.outputStream = outputStream;
        this.annotationStream = annotationStream;
        
        ENV = new Environment();
        b = new Boundary();
        deferred = Streams.<AuditEvent> defer().env(ENV).dispatcher(Environment.RING_BUFFER).get();
        Stream<AuditEvent> stream = deferred.compose();
        stream.consume(b.bind(new Consumer<AuditEvent>() {
            @Override
            public void accept(AuditEvent event) {
                outputStream.write(event);
            }
        }));
        
        batchBoundry = new Boundary();
        batchDeferred = Streams.<EventBatch> defer().env(ENV).dispatcher(Environment.RING_BUFFER).get();
        Stream<EventBatch> batchStream = batchDeferred.compose();
        batchStream.consume(batchBoundry.bind(new Consumer<EventBatch>() {
            @Override
            public void accept(EventBatch batch) {
                outputStream.writeBatch(batch);
            }
        }));
    }


    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.Event)
     *
     */
    @Override
    public AsyncAuditOutputStream write(AuditEvent event) {
        if (event instanceof AnnotationAuditEvent) {
            annotationStream.write((AnnotationAuditEvent) event);
        } else {
            deferred.accept(event);
            b.await();
        }
        
        return this;
    }

    @Override
    public AuditOutputStream<AuditEvent> writeBatch(EventBatch batch) {
       batchDeferred.accept(batch);
       batchBoundry.await();
       return this;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     */
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     *
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
    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#clone()
     *
     */
    @Override
    public Object clone() {
        return null;
    }

    

}
