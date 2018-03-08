/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
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

package org.audit4j.core.layout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.util.JMTEDateRendrer;

import com.floreysoft.jmte.Engine;

/**
 * The Class CustomizableLayout.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class CustomizableLayout implements Layout {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8488465359841862664L;

    /** The Constant DEFAULT_TEMPLATE. */
    private static final String DEFAULT_TEMPLATE = "${eventDate}|${uuid}|${actor}|${action}|${origin} => ${foreach fields field}${field.name} ${field.type}:${field.value}, ${end}";

    /** The engine. */
    private Engine engine;

    /** The date format. */
    private String dateFormat = CoreConstants.DEFAULT_DATE_FORMAT;

    //this getter is useful for yml file configuration
    public String getDateFormat() {
		return dateFormat;
	}


	/** The template. */
    private String template = DEFAULT_TEMPLATE;

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.Initializable#init()
     */
    @Override
    public void init() throws InitializationException {
        engine = Engine.createCachingEngine();
        JMTEDateRendrer dateRendrer = new JMTEDateRendrer();
        dateRendrer.setDateFormat(dateFormat);
        engine.registerRenderer(Date.class, dateRendrer);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.Initializable#stop()
     */
    @Override
    public void stop() {
        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.layout.Layout#format(org.audit4j.core.dto.AuditEvent)
     */
    @Override
    public String format(AuditEvent event) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("eventDate", event.getTimestamp());
        model.put("uuid", event.getUuid());
        model.put("action", event.getAction());
        model.put("origin", event.getOrigin());
        model.put("actor", event.getActor());
        model.put("fields", event.getFields());
        return engine.transform(template, model);
    }

    /**
     * Sets the date format.
     * 
     * @param dateFormat
     *            the new date format
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Sets the template.
     * 
     * @param template
     *            the new template
     */
    public void setTemplate(String template) {
        this.template = template;
    }
    
    
    //this getter is useful for yml file configuration
	public String getTemplate() {
		return template;
	}
}
