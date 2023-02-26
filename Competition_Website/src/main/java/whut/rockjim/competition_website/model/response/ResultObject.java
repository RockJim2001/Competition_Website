package whut.rockjim.competition_website.model.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装分页查询的返回类
 */
public class ResultObject<T> {
    /**
     * 一个page对象
     */
    private Page<T> page;

    public ResultObject(Page<T> page){
        this.page=page;
    }

    public Map<String,Object> getResultObject(){
        Map<String,Object> resultObject=new HashMap<>();
        resultObject.put("pageNo",page.getCurrent());
        resultObject.put("pageSize",page.getSize());
        resultObject.put("total",page.getTotal());
        resultObject.put("totalPage",page.getTotal()/page.getSize()+1);
        resultObject.put("rows",page.getRecords());
        return resultObject;
    }
}
