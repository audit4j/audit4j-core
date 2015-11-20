package org.audit4j.core.util;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.Audit4jTestBase;
import org.audit4j.core.ObjectSerializer;
import org.audit4j.core.ObjectToFieldsSerializer;
import org.audit4j.core.Mock.TestChildObjectMock;
import org.audit4j.core.Mock.TestSuperObjectMock;
import org.audit4j.core.dto.Field;
import org.junit.Test;

public class ObjectToFieldsSerializerTest extends Audit4jTestBase {

    @Test
    public void testToString() {
        ObjectSerializer serializer = new ObjectToFieldsSerializer();
        watchStart("ObjectToFieldsSerializerTest");
        List<Field> fields = new ArrayList<>();
        serializer.serialize(fields, TestSuperObjectMock.create(), "superMock", null);
        watchStop();
        for (Field field : fields) {
            System.out.println(field.getName() + ": " + field.getValue());
            // System.out.println(field.getValue());
            // System.out.println(field.getType());
        }
    }

    @Test
    public void testToStringPrimitive() {
        ObjectSerializer serializer = new ObjectToFieldsSerializer();
        watchStart("ObjectToFieldsSerializerTest");
        List<Field> fields = new ArrayList<>();
        serializer.serialize(fields, "John", "user", null);
        watchStop();
        for (Field field : fields) {
            System.out.println(field.getName() + ": " + field.getValue());
        }
    }

    @Test
    public void testToFieldsObjectArray() {
        ObjectSerializer serializer = new ObjectToFieldsSerializer();

        List<TestChildObjectMock> childMocks = new ArrayList<>();
        childMocks.add(new TestChildObjectMock("test1", 10));
        childMocks.add(new TestChildObjectMock("test2", 20));

        watchStart("ObjectToFieldsSerializerTest");
        List<Field> fields = new ArrayList<>();
        serializer.serialize(fields, childMocks, "childMocks", null);
        watchStop();
        for (Field field : fields) {
            System.out.println(field.getName() + ": " + field.getValue());
        }
    }
}
