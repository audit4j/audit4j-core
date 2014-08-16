/*
 * Copyright 2014 Janith Bandara, This source is a part of 
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

package org.audit4j.core;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AsyncAuditMessage;
import org.audit4j.core.dto.AsyncCallAuditDto;
import org.audit4j.core.dto.AuditBase;

/**
 * @deprecated due to new Streaming api. no longer using.
 * The Class AsyncAuditEngine.
 * 
 * @author Janith Bandara
 * 
 * @since 1.0
 */
@Deprecated
public class AsyncAuditEngine implements ExceptionListener {

	/** The queue name. */
	private String queueName = "AUDIT_QUEUE";

	/** The audit engine. */
	private static AsyncAuditEngine auditEngine = null;

	/** The log. */
	protected static Log log = LogFactory.getLog(AsyncAuditEngine.class);

	/** The destination. */
	protected Destination destination = null;

	/** The session. */
	protected Session session = null;

	/** The connection factory. */
	protected ActiveMQConnectionFactory connectionFactory;

	/** The connection. */
	Connection connection = null;

	/** The audit processor. */
	private AuditProcessor<AuditBase> auditProcessor;

	/**
	 * Instantiates a new async audit engine.
	 */
	private AsyncAuditEngine() {

	}

	/**
	 * Inits the.
	 */
	protected void init() {
		initializeIfNeeded();
		listen();
	}

	/**
	 * Listen.
	 */
	protected void listen() {
		try {
			final MessageConsumer consumer = session.createConsumer(destination);

			final MessageListener listener = new MessageListener() {

				@Override
				public void onMessage(final Message message) {
					try {
						final ObjectMessage objectMessage = (ObjectMessage) message;
						final Object object = objectMessage.getObject();
						if (object instanceof AsyncAuditMessage) {
							AsyncAuditMessage auditMessage = (AsyncAuditMessage) object;
							AuditEventProcessor eventProcessor = AuditEventProcessor.getInstance();
							eventProcessor.setConf(auditMessage.getConf());
							eventProcessor.process(auditMessage.getEvent());
						} else if (object instanceof AnnotationAuditEvent) {
							AsynchronousAnnotationProcessor processor = AsynchronousAnnotationProcessor.getInstance();
							processor.process((AnnotationAuditEvent) object);
						} else if (object instanceof AsyncCallAuditDto) {
							AsynchronousCallAuditProcessor callProcessor = AsynchronousCallAuditProcessor.getInstance();
							callProcessor.process((AsyncCallAuditDto) object);
						}
					} catch (final JMSException e) {
						e.printStackTrace();
					}
					try {
						message.acknowledge();
					} catch (final JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Received Message");
				}
			};

			consumer.setMessageListener(listener);

		} catch (final Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Send.
	 * 
	 * @param t
	 *            the t
	 */
	protected void send(final Serializable t) {
		try {
			final MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			final ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(t);

			// Tell the producer to send the message
			System.out.println("Sent message: " + t.hashCode());
			producer.send(objectMessage);

		} catch (final Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Initialize if needed.
	 */
	protected synchronized void initializeIfNeeded() {
		try {
			if (session == null) {
				// Create a ConnectionFactory
				connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
				connection = connectionFactory.createConnection();
				connection.start();
				connection.setExceptionListener(this);
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				destination = session.createQueue(queueName);
			} else {
				log.trace("messageListener already defined");
			}

		} catch (final JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.ExceptionListener#onException(javax.jms.JMSException)
	 */
	@Override
	public void onException(final JMSException arg0) {
		
	}

	/**
	 * Sets the queue name.
	 * 
	 * @param queueName
	 *            the new queue name
	 */
	protected void setQueueName(final String queueName) {
		this.queueName = queueName;
	}

	/**
	 * Gets the audit processor.
	 * 
	 * @return the audit processor
	 */
	protected AuditProcessor<AuditBase> getAuditProcessor() {
		return this.auditProcessor;
	}

	/**
	 * Sets the audit processor.
	 * 
	 * @param auditProcessor
	 *            the new audit processor
	 */
	protected void setAuditProcessor(final AuditProcessor<AuditBase> auditProcessor) {
		this.auditProcessor = auditProcessor;
	}

	/**
	 * Gets the single instance of AsyncAuditEngine.
	 * 
	 * @return single instance of AsyncAuditEngine
	 */
	protected static AsyncAuditEngine getInstance() {
		synchronized (AsyncAuditEngine.class) {
			if (auditEngine == null) {
				auditEngine = new AsyncAuditEngine();
				auditEngine.init();
			}
		}
		return auditEngine;
	}
}
