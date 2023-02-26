package whut.rockjim.competition_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.mapper.MyCompetitionMapper;
import whut.rockjim.competition_website.model.dto.MyCompetitionDTO;
import whut.rockjim.competition_website.model.dto.NewDTO;
import whut.rockjim.competition_website.model.entity.Competition;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.response.ResultObject;
import whut.rockjim.competition_website.model.vo.CompetitionDetailVO;
import whut.rockjim.competition_website.model.vo.CompetitionListVO;
import whut.rockjim.competition_website.service.CompetitionService;
import whut.rockjim.competition_website.mapper.CompetitionMapper;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.utils.AliyunOSSUtil;

import java.util.*;

/**
* @author 25760
* @description 针对表【competition】的数据库操作Service实现
* @createDate 2022-04-27 15:13:52
*/
@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition>
    implements CompetitionService{
    //生成一个AliyunOssUtil工具类对象
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;
    //生成一个自定义Mapper类
    @Autowired
    private MyCompetitionMapper myCompetitionMapper;


    /**
     * 获取竞赛列表用户展示
     * @param result
     */
    public void getList(Result result){
        List<CompetitionListVO> competitionListVOList=new ArrayList<>();
        List<Competition> competitionList= this.list();
        for (Competition competition:competitionList) {
            CompetitionListVO competitionListVO=new CompetitionListVO(competition);
            competitionListVOList.add(competitionListVO);
        }
        result.setSuccess(true);
        result.setMessage("成功获取竞赛列表");
        result.setData(competitionListVOList);
    };

    /**
     * 通过id参数获取竞赛的详细信息
     * @param id
     * @param result
     */
    public void getDetail(Long id, Result result){
        Competition competition=this.getById(id);
        if(competition==null){
            result.setSuccess(false);
            result.setMessage("编号为："+id+"的竞赛不存在！");
        }else{
            result.setSuccess(true);
            result.setMessage("成功获取编号为："+id+"的竞赛信息！");
            result.setData(new CompetitionDetailVO(competition));
        }
    }

    /**
     * 以竞赛发布时间的倒序得到资讯列表
     * @param result
     */
    @Override
    public void getListOrderByPublishDate(Result result) {
        try{
            List<Competition> competitionList=this.list(new QueryWrapper<Competition>()
                    .orderByAsc("release_date"));
            List<NewDTO> newDTOList=new ArrayList<>();
            for (Competition item :competitionList) {
                newDTOList.add(new NewDTO(item));
            }
            result.setSuccess(true);
            result.setMessage("成功获取头条资讯列表");
            result.setData(newDTOList);
        }catch (Exception e){
            System.out.println(e);
            result.setSuccess(false);
            result.setMessage("服务器异常");
        }
    }

    /**
     * 分页查询所有竞赛的详细信息
     * @param pagination
     * @param result
     */
    @Override
    public void getDetailList(Pagination pagination, Result result) {
        Page<Competition> page=new Page<>(pagination.getPageNo(),pagination.getPageSize());
        Long id=pagination.getId();
        if(id==null)
        {this.baseMapper.selectPage(page,null);}
        else{
            this.baseMapper.selectPage(page,new QueryWrapper<Competition>().eq("organizer_id",id));
        }
        List<CompetitionDetailVO> detailVOList=new ArrayList<>();
        for (Competition item : page.getRecords()) {
            detailVOList.add(new CompetitionDetailVO(item));
        }

        ResultObject<Competition> resultObject=new ResultObject<>(page);

        //结果的返回
        result.setSuccess(true);
        result.setMessage("获取分页数据成功");
        result.setData(resultObject.getResultObject());
    }

    /**
     * 发布或修改一个竞赛
     * @param multipartFile
     * @param competition
     * @param result
     */
    @Override
    public void saveOrUpdateOne(MultipartFile multipartFile, Competition competition, Result result) {

        //先判断竞赛数据是否存在
        Competition temp=this.getById(competition.getId());

        if(temp!=null && multipartFile!=null){ //修改竞赛数据，先删除云上的poster数据
            if(aliyunOSSUtil.deleteFile(temp.getPoster())){
                System.out.println("删除成功！");
            }else{
                System.out.println("删除失败！");
            };
        }
        String url=null;
        if(temp!=null){
            url=temp.getPoster();
        }
        if(multipartFile!=null){
            url=aliyunOSSUtil.upload("competition/poster",multipartFile);
        }
        if(url!=null){
            System.out.println("上传成功！");
            //调用服务添加竞赛
            competition.setPoster(url);
            if(this.saveOrUpdate(competition)){
                result.setSuccess(true);
                result.setMessage("操作成功");
                result.setData(competition);
            }else
            {
                result.setSuccess(false);
                result.setMessage("操作失败！");
            }
        }else{

            System.out.println("上传失败！");
        }
    }

    /**
     * 通过修改竞赛的发布日期来发布竞赛
     * @param id
     * @param result
     */
    @Override
    public void publishOne(Long id, Result result) {
        Date nowDate=new Date(System.currentTimeMillis());
        Boolean flag=this.update(new UpdateWrapper<Competition>()
                .set("release_date",nowDate).eq("id",id));
        if(flag){
            result.setSuccess(true);
            result.setMessage("竞赛发布成功！");
        }else{
            result.setSuccess(false);
            result.setMessage("竞赛发布失败！");
        }
    }

    @Override
    public Boolean addRegistrationNumber(Long competitionId) {
        return this.baseMapper.addRegistrationNumber(competitionId);
    }

    @Override
    public void deleteOne(Long id, Result result) {
        String url=this.getById(id).getPoster();
        Boolean flag= this.removeById(id);
        if(flag){
            result.setSuccess(true);
            result.setMessage("竞赛删除成功！");
            //删除云服务存储的宣传图
            aliyunOSSUtil.deleteFile(url);
        }
        else{
            result.setMessage("系统异常！");
            result.setSuccess(false);
        }
    }

    /**
     * 根据用户id查找该用户的参赛信息
     * @param pagination
     * @param result
     */
    @Override
    public void getCompetitionInfoById(Pagination pagination, Result result) {
        Page<MyCompetitionDTO> page=new Page<>();
        page.setSize(pagination.getPageSize());
        page.setCurrent(pagination.getPageNo());
        Long id=pagination.getId();
        Page<MyCompetitionDTO> list=myCompetitionMapper.getCompetitionById(page,id);
        ResultObject<MyCompetitionDTO> resultObject=new ResultObject<>(list);
        result.setMessage("查询成功！");
        result.setSuccess(true);
        result.setData(resultObject);
    }

}




