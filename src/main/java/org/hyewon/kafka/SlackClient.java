package org.hyewon.kafka;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;

public class SlackClient {
    private static final Slack slack = Slack.getInstance();
    private final MethodsClient methodsClient;

    public SlackClient() {
        this.methodsClient = slack.methods();
    }

    public void sendSlack(String channel, String message) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel(channel)
            .text(message)
            .build();

        try {
            ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
        } catch (SlackApiException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendSlack(String token, String channel, String message) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .token(token).channel(channel)
                .text(message)
                .build();

        try {
            ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
        } catch (SlackApiException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
