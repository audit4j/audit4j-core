package org.audit4j.core.io;

import org.audit4j.core.dto.AuditEvent;

import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.function.Consumer;
import reactor.function.support.Boundary;

public class AsyncAuditOutputStream implements AuditOutputStream {
	final AuditOutputStream outputStream;
	Deferred<AuditEvent, Stream<AuditEvent>> deferred = null;
	static final Environment ENV = new Environment();
	Boundary b = null;

	public AsyncAuditOutputStream(final AuditOutputStream outputStream) {
		this.outputStream = outputStream;
		b = new Boundary();
		deferred = Streams.<AuditEvent> defer().env(ENV).dispatcher(Environment.RING_BUFFER).get();
		Stream<AuditEvent> stream = deferred.compose();
		stream.consume(b.bind(new Consumer<AuditEvent>() {
			@Override
			public void accept(AuditEvent event) {
				outputStream.write(event);
			}
		}));
	}

	@Override
	public AsyncAuditOutputStream write(AuditEvent event) {
		deferred.accept(event);
		b.await();
		return this;
	}

	@Override
	public void close() {
		ENV.shutdown();
		outputStream.close();
	}

	@Override
	public Object clone() {
		return null;
	}
}
