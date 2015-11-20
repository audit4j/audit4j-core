package org.audit4j.core;

import java.util.List;

import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.dto.Field;

public interface ObjectSerializer {

    void serialize(List<Field> auditFields, Object object, String objectName, DeIdentify deidentify);
}
