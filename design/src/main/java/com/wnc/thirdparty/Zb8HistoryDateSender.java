
package com.wnc.thirdparty;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Zb8HistoryDateSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String day) {
        System.out.println("Sender object: " + day);
        this.rabbitTemplate.convertAndSend("zb8_news_history_date_2", day);
    }
}