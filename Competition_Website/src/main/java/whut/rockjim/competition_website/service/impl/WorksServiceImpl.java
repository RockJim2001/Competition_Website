package whut.rockjim.competition_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.mapper.ScoreMapper;
import whut.rockjim.competition_website.mapper.WorksScoreByIdMapper;
import whut.rockjim.competition_website.mapper.WorksScoreNewMapper;
import whut.rockjim.competition_website.model.dto.ScoreDTO;
import whut.rockjim.competition_website.model.dto.WorksScoreNewDTO;
import whut.rockjim.competition_website.model.entity.Competition;
import whut.rockjim.competition_website.model.entity.Works;
import whut.rockjim.competition_website.model.entity.WorksScore;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.request.ScoreRequest;
import whut.rockjim.competition_website.model.request.UpdateWorksRequest;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.response.ResultObject;
import whut.rockjim.competition_website.model.vo.CompetitionDetailVO;
import whut.rockjim.competition_website.service.UsersService;
import whut.rockjim.competition_website.service.WorksScoreService;
import whut.rockjim.competition_website.service.WorksService;
import whut.rockjim.competition_website.mapper.WorksMapper;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.utils.AliyunOSSUtil;

import java.util.*;

/**
* @author 25760
* @description 针对表【works】的数据库操作Service实现
* @createDate 2022-05-09 23:15:39
*/
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works>
    implements WorksService{

    //生成一个云服务对象
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;
    //生成一个UsersService服务对象
    @Autowired
    private UsersService usersService;
    //生成一个WorksScoreService服务对象
    @Autowired
    private WorksScoreService worksScoreService;

    @Autowired
    private WorksScoreNewMapper worksScoreNewMapper;
    @Autowired
    private WorksScoreByIdMapper worksScoreByIdMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public void submit(MultipartFile multipartFile, Works works, Result result) {
        //重复上传的话会覆盖
        Long userId=works.getUserId();
        Long competitionId=works.getCompetitionId();
        Works oldWork= this.getOne(new QueryWrapper<Works>()
                .eq("user_id",userId)
                .eq("competition_id",competitionId));
        if(oldWork!=null){
            //先删除云服务上的数据
            aliyunOSSUtil.deleteFile(oldWork.getWorksUrl());
            System.out.println("查询到哦");
            works.setId(oldWork.getId());
        }
        //先将文件上传到云服务
        String url=aliyunOSSUtil.upload("competition/works",multipartFile);
        if(url!=null){
            works.setWorksUrl(url);
            if(this.saveOrUpdate(works)){
                result.setSuccess(true);
                result.setMessage("提交成功！");
            }else{
                result.setSuccess(false);
                result.setMessage("系统异常！");
            };
        }else{
            result.setSuccess(false);
            result.setMessage("云服务异常！");
        }
    }

    @Override
    public void worksList(Pagination pagination, Result result) {
        Page<Works> page=new Page<>();
        this.baseMapper.selectPage(page,null);



       ResultObject<Works> resultObject=new ResultObject<>(page);

        //结果的返回
        result.setSuccess(true);
        result.setMessage("获取分页数据成功");
        result.setData(resultObject.getResultObject());
    }

    @Override
    public void updateWorksJudges(UpdateWorksRequest updateWorksRequest, Result result) {
        //首先判断分配方式
        Integer flag=updateWorksRequest.getAllocation();
        List<Long> judges;

        if(flag==1){//系统分配--随机分配五名评委
            List<Integer> count=new ArrayList<>();
            count.add(3);count.add(4);count.add(5);count.add(6);
            Collections.shuffle(count);
            judges=usersService.randomJudgeList(count.get(0));

        }else{//人工分配
            judges=updateWorksRequest.getJudges();
        }
        //向works_score表中添加数据
        for (Long l:judges) {
            WorksScore worksScore=new WorksScore();
            worksScore.setWorksId(updateWorksRequest.getId());
            worksScore.setUserId(l);
            //添加数据
            worksScoreService.save(worksScore);
        }

        this.update(new UpdateWrapper<Works>()
                .eq("id",updateWorksRequest.getId())
                .set("allocation",flag));

        result.setMessage("评委分配成功！");
        result.setSuccess(true);
    }

    @Override
    public  void getWorksScoreById(Pagination pagination,Result result) {
        Page<WorksScoreNewDTO> list=new Page<>();
        list.setSize(pagination.getPageSize());
        list.setCurrent(pagination.getPageNo());
        Long id=pagination.getId();
        Page<WorksScoreNewDTO> page=new Page<>();
        if(id==null){
           page =worksScoreNewMapper.getWorksScore(list);
        }else{
            System.out.println("通过id查询");
            page=worksScoreByIdMapper.getWorksScoreById(list,id);
        }

        ResultObject<WorksScoreNewDTO> resultObject=new ResultObject<>(page);

        //结果的返回
        result.setSuccess(true);
        result.setMessage("获取分页数据成功");
        result.setData(resultObject.getResultObject());
    }

    /**
     * 分页获取评分列表
     * @param pagination
     * @param result
     */
    @Override
    public void getScoreList(Pagination pagination, Result result) {
        Page<ScoreDTO> page=new Page<>();
        page.setCurrent(pagination.getPageNo());
        page.setSize(pagination.getPageSize());

        Long id=pagination.getId();
        Page<ScoreDTO> list=new Page<>();
        if(id==null)
        {
            list=scoreMapper.getScoreList(page);
        }else{
            list=scoreMapper.getScoreListById(page,id);
        }


        ResultObject<ScoreDTO> resultObject=new ResultObject<>(list);

        result.setMessage("获取评分列表成功！");
        result.setSuccess(true);
        result.setData(resultObject.getResultObject());
    }

    /**
     * 打分操作
     * @param scoreRequest
     * @param result
     */
    @Override
    public void updateScore(ScoreRequest scoreRequest, Result result) {
        Date nowDate=new Date();
        Boolean flag=worksScoreService.update(new UpdateWrapper<WorksScore>()
                .eq("id",scoreRequest.getId())
                .set("score",scoreRequest.getScore())
                .set("comment",scoreRequest.getComment())
                .set("date",nowDate)
                .set("status",1));
        if(flag){
            result.setSuccess(true);
            result.setMessage("打分成功！");
        }else{
            result.setSuccess(false);
            result.setMessage("系统异常！");
        }
    }

    /**
     * 得分汇总
     * @param id
     */
    @Override
    public void summaryScore(Long id) {
        this.baseMapper.summaryScore(id);
    }

    /**
     * 根据id查询作品得分
     * @param id
     * @param result
     */
    @Override
    public void inquiryScoreById(Long id, Result result) {
        Double score=this.getOne(new QueryWrapper<Works>()
                .eq("id",id)).getScore();
        result.setSuccess(true);
        if(score==0){
            result.setMessage("未到结果公布时间！！！");
        }else{
            result.setMessage("结果查询成功！");
            result.setData(score);
        }
    }
}




