package org.example.app.function;

import org.apache.flink.api.common.functions.util.ListCollector;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStatelessFunction {

    @Test
    public void testMap() throws Exception {
        StatelessMap statelessMap = new StatelessMap();
        String out = statelessMap.map("world");
        assertEquals("hello world", out);
    }

    @Test
    public void testFlatMap() throws Exception {
        StatelessFlatMap statelessFlatMap = new StatelessFlatMap();
        List<String> out = new ArrayList<>();
        ListCollector<String> listCollector = new ListCollector<>(out);
        statelessFlatMap.flatMap("world", listCollector);
        assertEquals(Lists.newArrayList("hello world"), out);
    }

}
