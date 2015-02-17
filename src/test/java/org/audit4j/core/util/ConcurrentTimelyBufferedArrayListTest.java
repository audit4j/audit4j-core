package org.audit4j.core.util;

import java.util.List;

import org.audit4j.core.util.ConcurrentTimelyBufferedArrayList.BufferedListener;
import org.junit.Test;

public class ConcurrentTimelyBufferedArrayListTest {

    @Test
    public void testList() {
        ConcurrentTimelyBufferedArrayList<String> list = new ConcurrentTimelyBufferedArrayList<>(10,
                new BufferedListener<String>() {
                    @Override
                    public void accept(List<String> buffered) {
                        System.out.println(buffered.toString());
                    }
                });
        
        list.add("1");
        list.add("2");
        list.add("3");
    }
}
