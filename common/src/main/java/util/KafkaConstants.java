package util;

public class KafkaConstants {
    public static final String KAFKA_CONSUMER_HOST = "kafka.default.svc.cluster.local:9092";
    public static final String KAFKA_PRODUCER_HOST =
            "kafka-controller-0.kafka-controller-headless.default.svc.cluster.local:9092," +
                    "kafka-controller-1.kafka-controller-headless.default.svc.cluster.local:9092," +
                    "kafka-controller-2.kafka-controller-headless.default.svc.cluster.local:9092";
    public static final String BOOK_SEND_MODERATION_TOPIC = "book-send-moderation-topic";
    public static final String BOOK_SEND_MODERATION_RESULT_TOPIC = "book-send-moderation-result-topic";
}
