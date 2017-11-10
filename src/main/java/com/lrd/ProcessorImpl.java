package com.lrd;

import org.apache.kafka.streams.kstream.*;

import java.util.Arrays;
import java.util.Locale;

public class ProcessorImpl {

    public KStream<String,String> dataConvert(KStream<String,String> parentStream) {
        KStream<String,String> stream = parentStream
                .mapValues(new ValueMapper<String,String>() {
                    @Override
                    public String apply(String value) {
                        try {
                            Integer re = Integer.valueOf(value)+1;
                            return re.toString();
                        }
                        catch (Exception e){
                            System.out.println("convert error:"+e);
                            return null;
                        }
                    }
                });
        return stream;
    }

    public KStream<String,String> dataStatistic(KStream<String,String> parentStream){
        KStream<String, String> stream = parentStream
                .groupBy(new KeyValueMapper<String, String, String>() {
                    @Override
                    public String apply(String key, String value) {
                        return "key";
                    }
                })
                .reduce(new Reducer<String>() {
                    @Override
                    public String apply(String value1, String value2) {
                        try {
                            Integer sum = Integer.valueOf(value1)+Integer.valueOf(value2);
                            return sum.toString();
                        }
                        catch (Exception e){
                            System.out.println("statistic error:"+e);
                            return value1;
                        }

                    }
                },TimeWindows.of(10*1000))
                .toStream(new KeyValueMapper<Windowed<String>, String, String>() {
                    @Override
                    public String apply(Windowed<String> key, String value) {
                        return key.toString();
                    }
                });
        stream.print("statistic:");
        return stream;
    }

    public KStream<String,String> dataAlarm(KStream<String,String> parentStream){
        KStream<String, String> stream = parentStream
                .filter(new Predicate<String, String>() {
                    @Override
                    public boolean test(String key, String value) {
                        try {
                            if(Integer.valueOf(value) > 10){
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                        catch (Exception e){
                            System.out.println("alarm error:"+e);
                            return false;
                        }
                    }
                });
        stream.print("alarm:");
        return stream;
    }
}
