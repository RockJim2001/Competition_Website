package whut.rockjim.competition_website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut.rockjim.competition_website.model.entity.Competition;
import whut.rockjim.competition_website.service.CompetitionService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


@SpringBootTest
class CompetitionControllerTest {
    @Autowired
    private CompetitionService competitionService;

    /**
     * 发布一个竞赛
     */
    @Test
    public void addCompetition() throws IOException {
        Competition competition=new Competition();

        //从本地文本中读入数据
        String context=null;

        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\25760\\Desktop\\test.md")));
        String str;
        while ((str=reader.readLine())!=null){
            context+=str+"\r\n";
        }
        competition.setDetail(context);
        competition.setOrganizerId(1534400766566064131L);
        if(competitionService.saveOrUpdate(competition,null)){
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败！");
        }
    }

    @Test
    public  void add() throws IOException {
        for(int i=3;i<11;i++)
            addCompetition();
    }
}