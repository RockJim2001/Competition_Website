package whut.rockjim.competition_website.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.vo.RegistrationListVO;
import whut.rockjim.competition_website.service.RegistrationFormService;

/**
 * 处理学生参赛信息请求的类
 */
@Log4j2
@RestController
public class ParticipateController {

    //生成一个 服务对象
    @Autowired
    private RegistrationFormService registrationFormService;

    /**
     * 比赛的报名
     * @param multipartFile
     * @param userId
     * @param competitionId
     * @return
     */
//    @Transactional
    @PostMapping(value = "/apply")
    public Result applyOne(@RequestPart(value = "file",required = false) MultipartFile multipartFile,
                           @RequestParam(value = "userId") Long userId, @RequestParam(value = "competitionId")Long competitionId){
        log.info("用户>"+userId+"报名参加竞赛>"+competitionId);
        Result result=new Result();
        //调用服务
        registrationFormService.applyOne(multipartFile,userId,competitionId,result);
        return result;
    }

    /**
     * 分页查询返回报名列表
     * @param pagination
     * @return
     */
    @PostMapping(value = "/applyList")
    public Result applyList(@RequestBody Pagination pagination){
        log.info("通过分页查询获取报名列表");
        Result result=new Result();
        //调用服务
        registrationFormService.applyList(pagination,result);
        return result;
    }


    @PostMapping(value = "/updateApply")
    public Result updateApply(@RequestBody RegistrationListVO registrationListVO){
        log.info("更新一条报名信息："+registrationListVO.toString());
        Result result=new Result();
        //调用服务
        registrationFormService.updateApply(registrationListVO,result);
        return result;
    }

    @GetMapping(value = "/isRegister")
    public Result isRegister(@RequestParam Long user_id,@RequestParam Long competition_id){
        log.info("判断用户"+user_id+"是否已经报名");

        Result result=new Result();
        registrationFormService.isRegister(user_id,competition_id,result);
        return result;
    }
}
