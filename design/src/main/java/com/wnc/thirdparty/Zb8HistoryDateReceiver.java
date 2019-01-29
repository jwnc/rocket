
package com.wnc.thirdparty;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "zb8_news_history_date_2")
public class Zb8HistoryDateReceiver {

    @Autowired
    Zb8HistoryDateSender zb8HistoryDateSender;

    Logger logger = Logger.getLogger(Zb8HistoryDateReceiver.class);

    @RabbitHandler
    public void process(String day) {
        logger.info("Receiver object : " + day);
        try {
            logger.info(" : FunnyCmt Over-" + day);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(" : FunnyCmt-Err in " + day);
            // 异常的时候重新放入mq
            zb8HistoryDateSender.send(day);
        }
    }

}
