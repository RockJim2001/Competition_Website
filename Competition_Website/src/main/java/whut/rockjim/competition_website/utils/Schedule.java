package whut.rockjim.competition_website.utils;

import com.sun.jndi.cosnaming.CNCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import whut.rockjim.competition_website.model.entity.Competition;
import whut.rockjim.competition_website.service.CompetitionService;
import whut.rockjim.competition_website.service.WorksService;

import java.util.Date;
import java.util.List;

/**
 * 系统定时任务
 */

@Component
public class Schedule {
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private WorksService worksService;

    /**
     * 定时对竞赛的分数进行汇总
     */
//    @Scheduled(cron = "00 * * * * ? ") //每日凌晨汇总
    @Scheduled(cron = "0/20 * * * * ? ") //每20秒执行一次
    public void summaryScore(){
        //获取当前的时间
        Date nowDate=new Date();
        //先获取当前系统所有的竞赛
        List<Competition> competitionList=competitionService.list();

        for (Competition item : competitionList) {
            if(nowDate.compareTo(item.getResultDate())>0){
                //执行汇总
                worksService.summaryScore(item.getId());
            }
        }
     }
}
