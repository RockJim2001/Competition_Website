package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.dto.CompetitionBodyDTO;
import whut.rockjim.competition_website.model.dto.DateLineDTO;
import whut.rockjim.competition_website.model.entity.Competition;

import java.util.*;

/**
 * 竞赛详细信息展示VO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionDetailVO {
    /**
     * 竞赛id---唯一标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 竞赛举办方名称(预设）
     */
//    private String organizerName;

    /**
     * 宣传海报（图片）url
     */
    private String poster;

    /**
     * 名称
     */
    private String title;

    /**
     * 描述或者摘要
     */
    private String summary;

    /**
     * 竞赛级别：0-校赛、1-省赛、2-校赛
     */
    private Integer scale;
    /**
     * 竞赛当前的状态:0-未开始、1-正在进行、2-已经结束
     */
    private Integer status;

    /**
     * 报名人数
     */
    private Integer registrationNumber;

    /**
     * 竞赛时间结点:竞赛发布时间、报名开始时间、作品提交时间、评审开始时间、结果公布时间
     */
    private List<DateLineDTO> dateLine;


    /**
     * 竞赛内容
     */
    private List<CompetitionBodyDTO> content;

    public CompetitionDetailVO(Competition competition){
        this.id=competition.getId();
        this.poster=competition.getPoster();
        this.title=competition.getTitle();
        this.summary=competition.getSummary();
        this.scale=competition.getScale();
        //计算当前的状态
        Date nowDate=new Date();
        if(nowDate.compareTo(competition.getReleaseDate())<0){
            this.status=0;
        }else if(nowDate.compareTo(competition.getResultDate())>0){
            this.status=2;
        }else{
            this.status=1;
        }
        this.registrationNumber=competition.getRegistrationNumber();


        //时间结点
        dateLine=new ArrayList<>();
        dateLine.add(new DateLineDTO("发布",competition.getReleaseDate()));
        dateLine.add(new DateLineDTO("报名",competition.getRegistrationDate()));
        dateLine.add(new DateLineDTO("提交作品",competition.getSubmitDate()));
        dateLine.add(new DateLineDTO("评审",competition.getReviewDate()));
        dateLine.add(new DateLineDTO("结果公布",competition.getResultDate()));


        //竞赛内容
        content=new ArrayList<>();
        content.add(new CompetitionBodyDTO("竞赛信息",competition.getDetail()));
        content.add(new CompetitionBodyDTO("参赛指南",competition.getEntryGuidelines()));
        content.add(new CompetitionBodyDTO("结果公布",competition.getResult()));
    }
}
