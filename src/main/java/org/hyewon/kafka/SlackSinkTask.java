package org.hyewon.kafka;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SlackSinkTask extends SinkTask {
    private Map<String, String> config = new HashMap<>();

    private SlackClient slackClient = new SlackClient();
    private static final Logger log = LoggerFactory.getLogger(SlackSinkTask.class);

    private static final JsonConverter JSON_CONVERTER = new JsonConverter();

    static {
        JSON_CONVERTER.configure(Collections.singletonMap("schemas.enable", "false"), false);
    }

    @Override
    public String version() {
        return "0.0.1";
    }

    @Override
    public void start(Map<String, String> props) {
        this.config = props;
    }

    public void start(Map<String, String> props, Object client) {
        log.info("Starting SlackSinkTask.");
    }

    @Override
    public void open(Collection<TopicPartition> partitions) {
        log.debug("Opening the task for topic partitions: {}", partitions);
    }

    @Override
    public void put(Collection<SinkRecord> records) throws ConnectException {
        log.trace("Putting {} to Slack.", records);
        String token = this.config.get(SlackSinkConnectorConfig.SLACK_TOKEN_CONFIG);
        String configChannel = this.config.get(SlackSinkConnectorConfig.SLACK_CHANNEL_CONFIG);

        for (SinkRecord record : records) {
            log.trace("Kafka Message: {}", record);
            Map<String, String> recordData = recordToMap(record);

            if (recordData.isEmpty()) {
                log.error("Unable to convert record data into templatable message, skipping {}, {}", recordData, record);
                continue;
            }

            if (configChannel != null) {
                slackClient.sendSlack(token, configChannel, record.topic() + "에 새로운 메시지 발생~!!");

            } else {
                log.error("channel was null {}", configChannel);
            }
        }
    }

    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
        log.trace("Flushing data to Slack with the following offsets: {}", offsets);
    }

    @Override
    public void close(Collection<TopicPartition> partitions) {
        log.debug("Closing the task for topic partitions: {}", partitions);
    }

    @Override
    public void stop() throws ConnectException {
        log.info("Stopping SlackSinkTask.");
    }

    public static Map<String, String> recordToMap(SinkRecord record) {
        Object value = record.value();
        Map<String, String> normalizedMap = new HashMap<>();

        if (value instanceof Struct) {
            Struct struct = (Struct) value;
            normalizedMap.put("message", struct.getString("message"));
        }

        if (value instanceof String) {
            normalizedMap.put("message", (String) value);
        } else {
            normalizedMap.put("message", String.valueOf(value));
        }

        return normalizedMap;
    }

}