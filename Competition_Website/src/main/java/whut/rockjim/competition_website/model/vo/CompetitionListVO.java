package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import whut.rockjim.competition_website.model.entity.Competition;
import java.util.Date;

/**
 * 返回竞赛列表给前端
 */
@Data
public class CompetitionListVO {
    /**
     * 竞赛id---唯一标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 竞赛url
     */
    private String url;
    /**
     * 宣传海报（图片）的url
     */
    private String poster;
    /**
     * 竞赛级别：0-校赛、1-省赛、2-国赛
     */
    private Integer scale;
    /**
     * 名称
     */
    private String title;
    /**
     * 描述或者摘要
     */
    private String summary;
    /**
     * 竞赛当前的状态：0-未开始、1-正在进行、2-已经结束
     */
    private Integer status;
    /**
     * 报名人数
     */
    private Integer registrationNumber;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    /**
     *
     * @param competition
     */
    public CompetitionListVO(Competition competition){
        this.id=competition.getId();
        this.url=competition.getUrl();
        this.poster=competition.getPoster();
        this.title=competition.getTitle();
        this.summary=competition.getSummary();
        this.registrationNumber=competition.getRegistrationNumber();
        this.scale=competition.getScale();
        this.startDate=competition.getReleaseDate();
        this.endDate=competition.getResultDate();
        //计算当前的状态
        Date nowDate=new Date();
        if(nowDate.compareTo(this.startDate)<0){
            this.status=0;
        }else if(nowDate.compareTo(this.endDate)>0){
            this.status=2;
        }else{
            this.status=1;
        }
    }
}
