package whut.rockjim.competition_website.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回类 作为Respone
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{
    /**
     * 是否处理成功
     */
    private Boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回单一的数据对象
     */
    private T data;
    /**
     * 仅含有状态码、消息的结果集
     */
    public Result(Boolean success,String message){
        this.success=success;
        this.message=message;
        this.data=null;
    }
}
