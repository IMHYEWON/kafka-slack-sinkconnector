package org.hyewon.kafka;


import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlackSinkConnector extends SinkConnector {

    private Map<String, String> configProperties;

    @Override
    public String version() {
        return "0.0.1";
    }

    @Override
    public void start(Map<String, String> props) throws ConnectException {
        try {
            configProperties = props;
            // 설정 유효성 검사
            new SlackSinkConnectorConfig(props);
        } catch (ConfigException e) {
            throw new ConnectException(
                "Couldn't start due to configuration error", e
            );
        }
    }

    @Override
    public Class<? extends Task> taskClass() {
        return SlackSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();
        Map<String, String> taskProps = new HashMap<>();
        taskProps.putAll(configProperties);
        for (int i = 0; i < maxTasks; i++) {
            taskConfigs.add(new HashMap<>(taskProps));

        }
        return taskConfigs;
    }

    @Override
    public void stop() throws ConnectException {
    }

    @Override
    public ConfigDef config() {
        return SlackSinkConnectorConfig.CONFIG;
    }
}