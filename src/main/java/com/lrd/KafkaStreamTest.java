package com.lrd;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.*;

public class KafkaStreamTest {

    public static void main(final String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ProcessorImpl processor = new ProcessorImpl();
        Map<String, KStream<String,String>> kStreamMap = new HashMap<>(6);
        KStreamBuilder builder = new KStreamBuilder();
        kStreamMap.put("source",builder.stream("streams-plaintext-input"));
        kStreamMap.put("convert",processor.dataConvert(kStreamMap.get("source")));
        kStreamMap.put("statistic",processor.dataStatistic(kStreamMap.get("convert")));
        kStreamMap.put("alarm",processor.dataAlarm(kStreamMap.get("statistic")));
        kStreamMap.put("out",kStreamMap.get("alarm").map(new KeyValueMapper<Object, Object, KeyValue<? extends String,? extends String>>() {
            @Override
            public KeyValue<? extends String, ? extends String> apply(Object key, Object value) {
                return KeyValue.pair(key.toString(),value.toString());
            }
        }));

        kStreamMap.get("out").print("out");
        kStreamMap.get("out").to("streams-wordcount-output");

        final KafkaStreams streams = new KafkaStreams(builder, props);
        streams.start();

    }

}
