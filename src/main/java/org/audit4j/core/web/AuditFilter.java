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

package org.audit4j.core.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.EventBuilder;

/**
 * The Class Auditfilter.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditFilter implements Filter {

    /** The user session attr name. */
    private String userSessionAttrName = null;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userSessionAttrName = filterConfig.getServletContext().getInitParameter("userSessionAttrName");

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        String actor = null;

        if (userSessionAttrName != null && !userSessionAttrName.equals("")) {
            HttpSession session = request.getSession(false);
            actor = (String) session.getAttribute("userSessionAttrName");
        }
        String ipAddress = request.getRemoteAddr();
        String url = request.getRequestURL().toString();

        EventBuilder builder = new EventBuilder();
        builder.addAction(url).addOrigin(ipAddress);
        if (actor == null) {
            builder.addActor(ipAddress);
        } else {
            builder.addActor(actor + "[" + ipAddress + "]");
        }

        Map<String, String[]> params = req.getParameterMap();

        for (final Map.Entry<String, String[]> entry : params.entrySet()) {
            builder.addField(entry.getKey(), entry.getValue());
        }

        AuditManager.getInstance().audit(builder.build());
        chain.doFilter(req, res);
    }

}
