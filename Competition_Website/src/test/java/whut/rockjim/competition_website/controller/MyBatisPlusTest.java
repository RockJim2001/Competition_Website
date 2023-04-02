package whut.rockjim.competition_website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut.rockjim.competition_website.mapper.UsersMapper;
import whut.rockjim.competition_website.mapper.WorksScoreNewMapper;
import whut.rockjim.competition_website.model.dto.WorksScoreDTO;
import whut.rockjim.competition_website.model.dto.WorksScoreNewDTO;
import whut.rockjim.competition_website.model.entity.Users;
import whut.rockjim.competition_website.model.entity.UsersAuths;
import whut.rockjim.competition_website.mapper.UsersAuthsMapper;
import whut.rockjim.competition_website.model.entity.WorksScore;
import whut.rockjim.competition_website.utils.AliyunOSSUtil;

import java.util.List;

@SpringBootTest
public class MyBatisPlusTest {

   @Autowired
   private AliyunOSSUtil aliyunOSSUtil;

    @Test
    public void getOne(){
        String url="https://competition-website.oss-cn-nanjing.aliyuncs.com/competition/works/116f73ed460944009146c049a43ed610.mp4";
        System.out.println(url);
       Boolean flag= aliyunOSSUtil.deleteFile(url);
        if(flag)
            System.out.println("success");
    }
}
