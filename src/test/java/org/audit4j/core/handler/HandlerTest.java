package org.audit4j.core.handler;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.core.exception.InitializationException;

public class HandlerTest extends Handler{

    private static final long serialVersionUID = 9065571812432575838L;


    
    @Override
    public void init() throws InitializationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handle() throws HandlerException {
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
