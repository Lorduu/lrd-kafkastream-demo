package com.lrd;
//
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Properties;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
public class KafkaStreamTest {

    @Test
    public void kafkaProducer(){
        Properties producerConfig = new Properties();
        producerConfig.put("bootstrap.servers", "localhost:9092");
        producerConfig.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer producer = new KafkaProducer<String, String>(producerConfig);
        for (int i=0;i<1000;i++){
            try {
                producer.send(new ProducerRecord<>("streams-plaintext-input","1","1"));
                System.out.println("发送成功"+i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
