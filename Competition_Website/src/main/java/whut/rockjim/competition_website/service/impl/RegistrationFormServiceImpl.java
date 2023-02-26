package whut.rockjim.competition_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.entity.RegistrationForm;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.response.ResultObject;
import whut.rockjim.competition_website.model.vo.RegistrationListVO;
import whut.rockjim.competition_website.service.CompetitionService;
import whut.rockjim.competition_website.service.RegistrationFormService;
import whut.rockjim.competition_website.mapper.RegistrationFormMapper;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.utils.AliyunOSSUtil;

import java.util.Date;

/**
* @author 25760
* @description 针对表【registration_form】的数据库操作Service实现
* @createDate 2022-05-08 21:22:40
*/
@Service
public class RegistrationFormServiceImpl extends ServiceImpl<RegistrationFormMapper, RegistrationForm>
    implements RegistrationFormService{

    //生成一个AliyunOSSUtil工具类的对象
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    //生成一个CompetitionService服务对象
    @Autowired
    private CompetitionService competitionService;

    /**
     * 参加一个竞赛
     * @param multipartFile
     * @param userId
     * @param competitionId
     * @param result
     */
    @Override
    public void applyOne(MultipartFile multipartFile, Long userId, Long competitionId, Result result) {
        //生成一个RegistrationForm对象用于向数据库中添加
        RegistrationForm registrationForm=new RegistrationForm();

        //赋值之后插入数据库


        //首先检查该用户是否已经报名过了
        registrationForm=this.getOne(new QueryWrapper<RegistrationForm>()
                .eq("user_id",userId)
                .eq("competition_id",competitionId));
        if(registrationForm!=null){
//           result.setMessage("不能重复报名！");
//            result.setSuccess(false);
//            result.setData(registrationForm);
//            return;
            //重复报名的话，覆盖掉前一次的报名信息
            aliyunOSSUtil.deleteFile(registrationForm.getApplyForm());
        }else{
            registrationForm=new RegistrationForm();
        }
        //先上传用户的报名表到云
       String url=aliyunOSSUtil.upload("competition/registration_form",multipartFile);
       if(url==null){
           result.setMessage("报名失败！");
           result.setSuccess(false);
           return;
       }

       registrationForm.setApplyForm(url);
       registrationForm.setUserId(userId);
       registrationForm.setCompetitionId(competitionId);
       registrationForm.setApplyDate(new Date());

       Boolean flag= this.saveOrUpdate(registrationForm);
       if(flag){
           result.setMessage("报名成功！");
           result.setSuccess(true);
           //报名成功的话，该竞赛报名人数+1
           competitionService.addRegistrationNumber(competitionId);
       }else{
           result.setSuccess(false);
           result.setMessage("数据库异常！");
       }
    }

    @Override
    public void applyList(Pagination pagination, Result result) {
        Page<RegistrationListVO> page=new Page<>(pagination.getPageNo(),pagination.getPageSize());
        Page<RegistrationListVO> pages=new Page<>();

        if(pagination.getId()==null){
            pages=page.setRecords(this.baseMapper.getList(page));
        }else{
            System.out.println("调用第二种");
            pages=page.setRecords(this.baseMapper.getListById(page,pagination.getId()));
        }

        ResultObject<RegistrationListVO> resultObject=new ResultObject<>(pages);

        //结果的返回
        result.setSuccess(true);
        result.setMessage("获取分页数据成功");
        result.setData(resultObject.getResultObject());
    }

    /**
     * 更新一条报名信息
     * @param registrationListVO
     * @param result
     */
    @Override
    public void updateApply(RegistrationListVO registrationListVO, Result result) {
       Boolean flag=this.update(new UpdateWrapper<RegistrationForm>()
                .set("status",registrationListVO.getStatus())
                        .set("comment",registrationListVO.getComment())
                .eq("id",registrationListVO.getId()));
       if(flag){
           result.setSuccess(true);
           result.setMessage("审核操作成功！");
       }else{
           result.setMessage("网络异常！");
           result.setSuccess(false);
       }
    }

    @Override
    public void isRegister(Long user_id, Long competition_id, Result result) {
      RegistrationForm registrationForm=this.baseMapper.selectOne(new QueryWrapper<RegistrationForm>()
                .eq("user_id",user_id)
                .eq("competition_id",competition_id));
      if(registrationForm==null){
          result.setMessage("当前为查询到该用户的报名信息，不能提交参赛作品！");
          result.setData(false);
      }else{
          result.setMessage("");
          result.setData(true);
      }
        result.setSuccess(true);
    }

}




