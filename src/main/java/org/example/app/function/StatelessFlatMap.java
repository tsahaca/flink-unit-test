package org.example.app.function;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class StatelessFlatMap implements FlatMapFunction<String, String> {
    public void flatMap(String in, Collector<String> collector) throws Exception {
        String out = "hello " + in;
        collector.collect(out);
    }
}