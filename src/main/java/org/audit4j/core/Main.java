package org.audit4j.core;

import org.audit4j.core.command.impl.MetadataCommand;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.io.AsyncAnnotationAuditOutputStream;
import org.audit4j.core.io.AsyncAuditOutputStream;
import org.audit4j.core.io.AuditEventOutputStream;
import org.audit4j.core.io.AuditOutputStream;
import org.audit4j.core.io.AuditProcessOutputStream;
import org.audit4j.core.io.MetadataLookupStream;

public class Main {

    static AuditOutputStream<AuditEvent> auditStream;
    
    public static void main(String[] args) {

       MetadataCommand command =  (MetadataCommand) PreConfigurationContext.getCommandByName("-metadata");
       if (command.isAsync()) {
           AsyncAnnotationAuditOutputStream asyncAnnotationStream = new AsyncAnnotationAuditOutputStream(new AuditProcessOutputStream(Context.getConfigContext()));
           MetadataLookupStream metadataStream = new MetadataLookupStream(new AuditProcessOutputStream(Context.getConfigContext()));
           AsyncAuditOutputStream asyncStream = new AsyncAuditOutputStream(metadataStream, asyncAnnotationStream);
           auditStream = new AuditEventOutputStream(asyncStream);
           
         //  auditStream.write(new AnnotationAuditEvent());
       } else {
           AsyncAnnotationAuditOutputStream asyncAnnotationStream = new AsyncAnnotationAuditOutputStream(new AuditProcessOutputStream(Context.getConfigContext()));
           AsyncAuditOutputStream asyncStream = new AsyncAuditOutputStream(new AuditProcessOutputStream(Context.getConfigContext()), asyncAnnotationStream);
           MetadataLookupStream metadataStream = new MetadataLookupStream(asyncStream);
           auditStream = new AuditEventOutputStream(metadataStream);
       }
      
    }
}
