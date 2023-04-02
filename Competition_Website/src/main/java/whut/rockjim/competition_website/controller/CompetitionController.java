package whut.rockjim.competition_website.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.entity.Competition;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.service.CompetitionService;

/**
 * 处理竞赛请求类，功能包括竞赛的发布、获取、与管理
 */
@Log4j2
@RestController
public class CompetitionController {


    //生成一个CompetitionService对象
    @Autowired
    private CompetitionService competitionService;

    /**
     * 获取所有竞赛信息
     * @return Result
     */
    @GetMapping(value = "/competition")
    public Result getAllCompetition(){
        log.info("获取所有竞赛请求:");
        Result result=new Result();
        //调用CompetitionService服务获取竞赛列表
        competitionService.getList(result);
        return result;
    }

    /**
     * 通过id获取竞赛详情
     * @param id
     * @return
     */
    @GetMapping(value = "/detail")
    public Result getCompetitionDetail(@RequestParam Long id){
        log.info("获取编号为："+id+"的竞赛详细信息");
        Result result=new Result();
        //调用CompetitionService服务获取竞赛详情
        competitionService.getDetail(id,result);
        return result;
    }


    /**
     * 获取竞赛列表
     * @return
     */
    @GetMapping(value = "/newsList")
    public Result getNewsList(){
        log.info("获取头条资讯列表:");
        Result result=new Result();
        //调用服务
        competitionService.getListOrderByPublishDate(result);
        return result;
    }

    /**
     * 通过分页获取所有竞赛的详细列表
     * @param pagination
     * @return
     */
    @PostMapping(value = "/competition/detailList")
    public Result getDetailList(@RequestBody Pagination pagination){
        log.info("通过分页获取所有竞赛的详细列表");
        Result result=new Result();
        //调用服务
        competitionService.getDetailList(pagination,result);
        return result;
    }

    /**
     * 新增或者更新一个竞赛
     * @param multipartFile
     * @param competition
     * @return
     */
    @Transactional//事件回滚
    @PostMapping(value = "/competition")
    public Result saveOrUpdateOne(@RequestPart(value = "file",required = false) MultipartFile multipartFile,@RequestPart(value = "competition") Competition competition){
        log.info("发布或修改一个竞赛："+competition.toString());
        log.info("获取到的文件："+multipartFile);
        Result result=new Result();
        //调用服务
        competitionService.saveOrUpdateOne(multipartFile,competition,result);
        return result;
    }

    /**
     * 竞赛发布（修改发布日期）
     * @param id
     * @return
     */
    @PatchMapping(value = "/competition")
    public Result publishOne(Long id){
        log.info("竞赛发布：\n"+"id:"+id);
        Result result=new Result();
        //调用服务
        competitionService.publishOne(id,result);
        return result;
    }

    @DeleteMapping(value = "/competition")
    public Result deleteOne(@RequestBody JSONObject jsonObject){
        Long id=Long.parseLong(jsonObject.getString("id"));
        log.info("删除编号为："+id+"的竞赛！");
        Result result=new Result<>();
        //调用服务
        competitionService.deleteOne(id,result);
        return result;
    }


    @PostMapping(value = "/competitionInfoById")
    public Result getCompetitionInfoById(@RequestBody Pagination pagination){
        log.info("获取用户"+pagination.getId()+"的所有竞赛信息");
        Result result=new Result();
        //调用服务
        competitionService.getCompetitionInfoById(pagination,result);
        return result;
    }
}
