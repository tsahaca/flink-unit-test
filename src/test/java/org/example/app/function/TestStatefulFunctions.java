package org.example.app.function;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.operators.StreamFlatMap;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.KeyedOneInputStreamOperatorTestHarness;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStatefulFunctions {


    @Test
    public void testFlatMap() throws Exception {
        StatefulFlatMap statefulFlatMap = new StatefulFlatMap();

        // OneInputStreamOperatorTestHarness takes the input and output types as type parameters
        OneInputStreamOperatorTestHarness<String, String> testHarness =
                // KeyedOneInputStreamOperatorTestHarness takes three arguments:
                //   Flink operator object, key selector and key type
                new KeyedOneInputStreamOperatorTestHarness<>(
                        new StreamFlatMap<>(statefulFlatMap), x -> "1", Types.STRING);
        testHarness.open();

        // test first record
        testHarness.processElement("world", 10);
        ValueState<String> previousInput =
                statefulFlatMap.getRuntimeContext().getState(
                        new ValueStateDescriptor<>("previousInput", Types.STRING));
        String stateValue = previousInput.value();
        assertEquals(
                Lists.newArrayList(new StreamRecord<>("hello world", 10)),
                testHarness.extractOutputStreamRecords());
        assertEquals("world", stateValue);

        // test second record
        testHarness.processElement("parallel", 20);
        assertEquals(
                Lists.newArrayList(
                        new StreamRecord<>("hello world", 10),
                        new StreamRecord<>("hello parallel world", 20)),
                testHarness.extractOutputStreamRecords());
        assertEquals("parallel", previousInput.value());
    }
}