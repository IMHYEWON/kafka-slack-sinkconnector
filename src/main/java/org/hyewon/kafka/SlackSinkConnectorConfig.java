package org.hyewon.kafka;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class SlackSinkConnectorConfig extends AbstractConfig {
    public static final String SLACK_TOKEN_CONFIG = "slack.token";
    public static final String SLACK_USER_CONFIG = "slack.username";
    public static final String SLACK_CHANNEL_CONFIG = "slack.channel";
    public static final String SLACK_BOT_IMAGE_CONFIG = "slack.image";
    public static final String MESSAGE_TEMPLATE_CONFIG = "message.template";

    public static final ConfigDef CONFIG;

    static {
        CONFIG = baseConfigDef();
    }

    public SlackSinkConnectorConfig(Map<String, String> props) {
        super(CONFIG, props);
    }

    protected static ConfigDef baseConfigDef() {
        ConfigDef configDef = new ConfigDef();
        addConnectorConfigs(configDef);
        return configDef;
    }

    private static void addConnectorConfigs(ConfigDef configDef) {
        final String group = "Connector";
        int order = 0;

        configDef.define(
            SLACK_TOKEN_CONFIG,
            ConfigDef.Type.STRING,
            null,
            ConfigDef.Importance.HIGH,
            "It's the most important thing",
            group,
            ++order,
            ConfigDef.Width.SHORT,
            "Slack Token"
        ).define(
            SLACK_USER_CONFIG,
            ConfigDef.Type.STRING,
            null,
            ConfigDef.Importance.HIGH,
            "Send to user",
            group,
            ++order,
            ConfigDef.Width.SHORT,
            "Slack User"
        ).define(
            SLACK_CHANNEL_CONFIG,
            ConfigDef.Type.STRING,
            null,
            ConfigDef.Importance.HIGH,
            "Send to channel",
            group,
            ++order,
            ConfigDef.Width.SHORT,
            "Slack User"
        ).define(
            MESSAGE_TEMPLATE_CONFIG,
            ConfigDef.Type.STRING,
            null,
            ConfigDef.Importance.HIGH,
            "Template to use",
            group,
            ++order,
            ConfigDef.Width.SHORT,
            "Template"
        );
    }
}

