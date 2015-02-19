package org.audit4j.core;

import org.audit4j.core.Mock.MetaDataMock;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.util.ToString;
import org.junit.Assert;
import org.junit.Test;

public class ObjectToStringTests extends Audit4jTestBase {

    @Test
    public void toStringTest() {
        AuditEvent event = new AuditEvent();
        event.setAction("Action");

        Assert.assertNotNull(ToString.toString("string"));
        Assert.assertEquals(
                "org.audit4j.core.dto.AuditEvent[actor=null,origin=null,action=Action,fields=java.util.ArrayList[elementData=class java.lang.Object[],size=0][modCount=0][][]][auditId=null,uuid=null,timestamp=null][]",
                ToString.toString(event));
        Assert.assertEquals("null", ToString.toString(null));
        Assert.assertEquals("test", ToString.toString("test"));
        Assert.assertEquals("1", ToString.toString(1));
        Assert.assertEquals("false", ToString.toString(false));
        Assert.assertEquals("true", ToString.toString(Boolean.TRUE));
        Assert.assertEquals("0.5", ToString.toString(0.5));
        Assert.assertEquals("a", ToString.toString('a'));
        String str = ToString.toString(getSampleAuditEvent());
        System.out.println(str);
    }

    @Test
    public void hasToStringTest() {
        Assert.assertTrue(ToString.hasToStringMethod(new MetaDataMock()));
        Assert.assertFalse(ToString.hasToStringMethod(new AuditEvent()));
    }
}
