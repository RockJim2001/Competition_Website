package whut.rockjim.competition_website.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut.rockjim.competition_website.model.entity.Notification;
import whut.rockjim.competition_website.service.NotificationService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class NotificationTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    public void  publishNotification() throws IOException {
        Notification notification=new Notification();

        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        notification.setPublishId(1534400766494760962L);
        notification.setPublishDate(new Date());
        notification.setTitle("武汉理工大学关于开展2021年全国普通高校大学生竞赛质量提升行动的通知");
        //从本地文本中读入数据
        String context=null;

        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\25760\\Desktop\\test.md")));
        String str;
        while ((str=reader.readLine())!=null){
            context+=str+"\r\n";
        }

        notification.setContext(context);

        notification.setReadingNumber(20);

        if(notificationService.save(notification)){
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败！");
        };

    }

    @Test
    public void addList() throws IOException {
        for (int i = 0; i < 20; i++) {
            publishNotification();
        }
    }
}
