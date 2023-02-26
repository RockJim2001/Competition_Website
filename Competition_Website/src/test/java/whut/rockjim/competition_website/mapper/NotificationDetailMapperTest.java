package whut.rockjim.competition_website.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut.rockjim.competition_website.model.dto.NotificationDTO;

@SpringBootTest
class NotificationDetailMapperTest {

    @Autowired
    private NotificationDetailMapper notificationDetailMapper;

    @Test
    public void test(){
        Page<NotificationDTO> page=new Page<>();
        page.setCurrent(1);
        page.setSize(5);

        Page<NotificationDTO> list=notificationDetailMapper.getNotificationDetailList(page);

        System.out.println(list.getTotal());
        for (NotificationDTO item :list.getRecords()) {
            System.out.println(item.toString());
        }
    }
}