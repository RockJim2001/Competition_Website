package whut.rockjim.competition_website.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 阿里云OSS配置类
 */
@Log4j2
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssConfig {
    /**
     * 访问控制id
     */
    private String accessKeyId;
    /**
     * 访问控制secret
     */
    private String accessKeySecret;
    /**
     * bucketName
     */
    private String bucketName;
    /**
     * 地域结点
     */
    private String endpoint;


    @PostConstruct
    public void init(){
        log.debug("oss: endpoint:{},accessKeyId:{},bucketName:{}", endpoint, accessKeyId, bucketName);
    }
}
