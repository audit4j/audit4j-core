package org.audit4j.core;

import org.audit4j.core.dto.AuditEvent;

public class MetadataHandler {

    public AuditEvent enhanceFromMetadata(final AuditEvent event){
        if (event.getActor() == null && Context.getConfigContext().hasMetadata()) {
            event.setActor(Context.getConfigContext().getMetaData().getActor());
        } else if (event.getActor() == null) {
            event.setActor(CoreConstants.DEFAULT_ACTOR);
        }
        
        if (event.getOrigin() == null && Context.getConfigContext().hasMetadata()) {
            event.setOrigin(Context.getConfigContext().getMetaData().getOrigin());
        } else if (event.getOrigin() == null) {
            event.setOrigin(CoreConstants.DEFAULT_ORIGIN);
        }
        return event;
    }
}
