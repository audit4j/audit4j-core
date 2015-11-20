package org.audit4j.core;


public class ObjectToStringTests extends Audit4jTestBase {

    /*@Test
    public void toStringTest() {
        AuditEvent event = new AuditEvent();
        event.setAction("Action");

        Assert.assertNotNull(ObjectToStringSerializer.toString("string"));
        Assert.assertEquals(
                "org.audit4j.core.dto.AuditEvent[actor=null,origin=null,action=Action,fields=java.util.ArrayList[elementData=class java.lang.Object[],size=0][modCount=0][][]][auditId=null,uuid=null,timestamp=null][]",
                ObjectToStringSerializer.toString(event));
        Assert.assertEquals("null", ObjectToStringSerializer.toString(null));
        Assert.assertEquals("test", ObjectToStringSerializer.toString("test"));
        Assert.assertEquals("1", ObjectToStringSerializer.toString(1));
        Assert.assertEquals("false", ObjectToStringSerializer.toString(false));
        Assert.assertEquals("true", ObjectToStringSerializer.toString(Boolean.TRUE));
        Assert.assertEquals("0.5", ObjectToStringSerializer.toString(0.5));
        Assert.assertEquals("a", ObjectToStringSerializer.toString('a'));
        String str = ObjectToStringSerializer.toString(getSampleAuditEvent());
        System.out.println(str);
    }

    @Test
    public void hasToStringTest() {
        Assert.assertTrue(ObjectToStringSerializer.hasToStringMethod(new MetaDataMock()));
        Assert.assertFalse(ObjectToStringSerializer.hasToStringMethod(new AuditEvent()));
    }*/
}
