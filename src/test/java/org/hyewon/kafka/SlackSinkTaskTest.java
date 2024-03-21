package org.hyewon.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.data.Timestamp;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class SlackSinkTaskTest {
    private final SlackSinkTask xf = new SlackSinkTask();

    public void testVersion() {
        String version = xf.version();
        assert(version.equals("0.0.1"));
    }

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testStart() {
    }

    @Test
    void testPut() {
        Map<String, String> props = new HashMap<>();
        props.put("slack.channel", "C06QLJ1S821");
        props.put("slack.token", "");
        props.put("slack.image", "rabbit");
        SlackSinkTask sinkTask = new SlackSinkTask();
        sinkTask.start(props);

        final Struct record = new Struct(SchemaBuilder.struct()
            .field("message", Schema.STRING_SCHEMA)
            .build())
            .put("message", "안뇽안뇽");


        // SinkRecord 생성
        final SinkRecord sinkRecord = new SinkRecord(
                "test-topic",             // 토픽 이름
                0,                        // 파티션 번호
                null,                     // 키
                null,                     // 타임스탬프
                Schema.STRING_SCHEMA,     // 메시지 스키마
                record,                   // 메시지 데이터
                0                         // 오프셋
        );
        Collection<SinkRecord> sinkRecords = new ArrayList<>();
        sinkRecords.add(sinkRecord);
        sinkRecords.add(sinkRecord);
        sinkTask.put(sinkRecords);

    }


}
