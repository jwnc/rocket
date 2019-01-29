package com.wnc.thirdparty;

import com.wnc.basic.BasicDateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {
    @Autowired
    Zb8HistoryDateSender zb8HistoryDateSender;

    @Test
    public void a() {
        String day = "2015-12-16";
        while (!day.equals("2099-12-31")) {
            day = BasicDateUtil.getDateBeforeDayDateString(day.replaceAll("-", ""), -1);
            day = day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6);
            zb8HistoryDateSender.send(day);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
