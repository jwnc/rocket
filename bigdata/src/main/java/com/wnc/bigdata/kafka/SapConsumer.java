package com.wnc.bigdata.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class SapConsumer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "10.104.132.12:9092");
        //每个消费者分配独立的组号
        properties.put("group.id", "0");
        //如果value合法，则自动提交偏移量
        properties.put("enable.auto.commit", "true");
        //设置多久一次更新被消费消息的偏移量
        properties.put("auto.commit.interval.ms", "1000");
        //设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
        properties.put("session.timeout.ms", "30000");
        //自动重置offset
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("saplogs"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(4000);
            for (ConsumerRecord<String, String> record : records) {
                System.err.printf("offset = %d, value = %s\n", record.offset(), record.value());
            }
            System.err.println("####### FETCH OVER , COUNT:" + records.count() + " #######");
        }

    }
}
