package org.audit4j.core.layout;

import org.audit4j.core.Audit4jTestBase;
import org.junit.Assert;
import org.junit.Test;

public class CustomizableLayoutTest extends Audit4jTestBase{

    @Test
    public void testFormat(){
        CustomizableLayout layout = new CustomizableLayout();
        layout.init();
        String formatted = layout.format(getSampleAuditEvent());
        Assert.assertNotNull(formatted);
        System.out.println(formatted);
    }
}
