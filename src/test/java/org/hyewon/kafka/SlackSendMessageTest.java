package org.hyewon.kafka;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.hyewon.kafka.SlackClient;
import org.junit.Test;

import java.io.IOException;

public class SlackSendMessageTest {
    public static final String SLACK_TOKENL = "mytoken";

    @Test
    public void sendMessageTest() throws SlackApiException, IOException {
        Slack slack = Slack.getInstance();
        MethodsClient methodsClient = slack.methods(SLACK_TOKENL);

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel("C03K3JLGQ7R") // 설정 가능
            .text("되나?") // 설정 가능
            .iconEmoji("potato") // 설정 가능
            .username("kafka bot") // 설정 가능
            .iconUrl("https://i.namu.wiki/i/VSLWjFqR-ILQ7UW6_Z4Ze1ehUuF6_QFlA-ettoE1b63mQYeiGMWrY034z3vHJV4McXWEE5ZEI8ztUcJE3FhwOcmCOTUDzKBPni_qJ-cHSIL3SeSuWjA1ZY09QcC66k1PAzcD0ZeNxnVBIjcPaKFBxw.webp")
            .build();

        ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
        System.out.println(response);

    }


    @Test
    public void sendMessage2() {
        SlackClient slackClient = new SlackClient();
        slackClient.sendSlack(SLACK_TOKENL, "C03K3JLGQ7R", ":wave: Hi from a bot written in Java! @한조");
    }
}
