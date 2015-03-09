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

import java.lang.reflect.Method;

import org.audit4j.core.AnnotationTransformer;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;

import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.function.Consumer;
import reactor.function.support.Boundary;

/**
 * The Class AnnotationAuditOutputStream.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.0.0
 */
public class AnnotationAuditOutputStream {

    /** The output stream. */
    AuditOutputStream outputStream;

    /** The annotation deferred. */
    Deferred<AnnotationAuditEvent, Stream<AnnotationAuditEvent>> annotationDeferred = null;

    /** The Constant ENV. */
    static final Environment ENV = new Environment();

    /** The b. */
    Boundary b = null;

    /**
     * Instantiates a new annotation audit output stream.
     * 
     * @param outputStream
     *            the output stream
     */
    public AnnotationAuditOutputStream(final AuditOutputStream outputStream) {
        this.outputStream = outputStream;
        b = new Boundary();

        annotationDeferred = Streams.<AnnotationAuditEvent> defer().env(ENV).dispatcher(Environment.RING_BUFFER).get();
        Stream<AnnotationAuditEvent> annostream = annotationDeferred.compose();
        annostream.consume(b.bind(new Consumer<AnnotationAuditEvent>() {
            @Override
            public void accept(AnnotationAuditEvent annotationEvent) {
                AnnotationTransformer transformer = new AnnotationTransformer();
                AuditEvent event = transformer.transformToEvent(annotationEvent);
                // event is null if annotation is not found
                if (event != null) {
                    outputStream.write(event);
                }
            }
        }));
    }

    /**
     * Write.
     * 
     * @param event
     *            the event
     * @return the annotation audit output stream
     */
    public AnnotationAuditOutputStream write(AnnotationAuditEvent event) {
        annotationDeferred.accept(event);
        b.await();
        return this;
    }

    /**
     * Write.
     * 
     * @param clazz
     *            the clazz
     * @param method
     *            the method
     * @param args
     *            the args
     * @return the annotation audit output stream
     */
    public AnnotationAuditOutputStream write(Class<?> clazz, Method method, Object[] args) {
        AnnotationAuditEvent event = new AnnotationAuditEvent();
        event.setClazz(clazz);
        event.setMethod(method);
        event.setArgs(args);
        annotationDeferred.accept(event);
        b.await();
        return this;
    }

    /**
     * Close.
     */
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
