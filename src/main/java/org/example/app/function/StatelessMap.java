package org.example.app.function;


import org.apache.flink.api.common.functions.MapFunction;

public class StatelessMap implements MapFunction<String, String> {
    public String map(String in) throws Exception {
        String out = "hello " + in;
        return out;
    }
}