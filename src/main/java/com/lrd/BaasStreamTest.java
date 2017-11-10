//package com.lrd;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
////import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.*;
//import org.apache.kafka.streams.kstream.internals.WindowedDeserializer;
//import org.apache.kafka.streams.kstream.internals.WindowedSerializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import sun.security.krb5.internal.tools.Ktab;
//
//import java.util.Arrays;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//public class BaasStreamTest {
//
//    final static Logger log = LoggerFactory.getLogger(WordCountApplication.class);
//    public static void main(final String[] args) throws Exception {
//        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "baas-streams-test");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        StreamsBuilder builder = new StreamsBuilder();
//
//        KStream<String,String> source = builder.stream("streams-plaintext-input-a");
//
//        ProcessorImpl processor = new ProcessorImpl() ;
//        KTable<Windowed<String>, Long> counts = source
//                .flatMapValues(new ValueMapper<String, Iterable<String>>() {
//                    @Override
//                    public Iterable<String> apply(String value) {
//                        return Arrays.asList(value.toLowerCase(Locale.getDefault()).split(" "));
//                    }
//                })
//                .groupBy(new KeyValueMapper<String, String, String>() {
//                    @Override
//                    public String apply(String key, String value) {
//                        return value;
//                    }
//                })
//                .windowedBy(TimeWindows.of(10*1000))
//                .count();
//        counts.toStream().print(Printed.toSysOut());
//
//
//        //KStream<String,String> temperature = processor.dataConvert(source);
//        //
//        //temperature.print(Printed.toSysOut());
//        //
//        //temperature.to( "streams-wordcount-output",Produced.with(Serdes.String(),Serdes.String()));
//
//        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        final CountDownLatch latch = new CountDownLatch(1);
//
//         //attach shutdown handler to catch control-c
//        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
//            @Override
//            public void run() {
//                streams.close();
//                latch.countDown();
//            }
//        });
//
//        try {
//            streams.start();
//            latch.await();
//        } catch (Throwable e) {
//            System.exit(1);
//        }
//        System.exit(0);
//
//    }
//
//}
