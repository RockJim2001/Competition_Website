package whut.rockjim.competition_website.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.config.AliyunOssConfig;

import javax.activation.MimetypesFileTypeMap;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 阿里云存储工具类
 * 提供文件上传、下载等功能
 */
@Log4j2
@Component
public class AliyunOSSUtil {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final long EXPIRTATION_TIME = 60*1000;
    private static final long CONTENT_LENGTH_RANGE_MIN = 0;
    private static final long CONTENT_LENGTH_RANGE_MAX = 1024*1024*1000;

    @Autowired
    private static AliyunOssConfig aliyunOssConfig;

    private static OSS getOssClient() {
        return new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessKeyId(), aliyunOssConfig.getAccessKeySecret());
    }

    /**
     * @Description: 判断文件是否存在
     * @Author: qy
     * @param fileName: 例如：filePath/fileName
     **/
    public boolean isExistFile(String fileName) {
        OSSClient ossClient = (OSSClient) getOssClient();
        boolean isExist = false;
        try {
            isExist = ossClient.doesObjectExist(aliyunOssConfig.getBucketName(), fileName);
            log.info("oss判断文件：{} 是否存在：{}", fileName,isExist);
        } catch (Exception e) {
            log.error("oss判断文件存在性异常", e);
        } finally {
            ossClient.shutdown();
        }
        return isExist;
    }

    /**
     * 上传
     */
    public String upload(String filePath,MultipartFile file) {
        OSSClient ossClient = (OSSClient) getOssClient();
        String fileName = file.getOriginalFilename();
        log.info("=========>OSS文件上传开始：" + fileName);
        String datePath = format.format(new Date());
        try {
            //容器不存在，就创建
            if (!ossClient.doesBucketExist(aliyunOssConfig.getBucketName())) {
                ossClient.createBucket(aliyunOssConfig.getBucketName());
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunOssConfig.getBucketName());
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();

            //在文件名称里面添加随机唯一值，使用UUID生成
             String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//            fileName =filePath+ "/" + datePath + "/" + fileName;
            fileName =filePath+ "/"  +uuid+ fileName.substring(fileName.lastIndexOf("."));

            // 如果需要上传时设置存储类型与访问权限
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunOssConfig.getBucketName(), fileName, inputStream);
            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(RequestUtil.getContentType(fileName.substring(fileName.lastIndexOf("."))));
            metadata.setContentType(new MimetypesFileTypeMap().getContentType(fileName.substring(fileName.lastIndexOf("."))));

            putObjectRequest.setMetadata(metadata);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            //设置权限 这里是公开读
            ossClient.setBucketAcl(aliyunOssConfig.getBucketName(), CannedAccessControlList.PublicRead);
            if (null != result) {
                String url = "https://" + aliyunOssConfig.getBucketName() + "." + aliyunOssConfig.getEndpoint() + "/" + fileName;
                log.info("==========>OSS文件上传成功,OSS地址：" + url);
                return url;
            }
        } catch (Exception e) {
            log.error("oss上传异常", e);
        } finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }


    /**
     * 删除
     */
    public boolean deleteFile(String fileName) {
        fileName= fileName.replace("https://competition-website.oss-cn-nanjing.aliyuncs.com/","");

        OSSClient ossClient = (OSSClient) getOssClient();
        log.info("=========>OSS文件删除开始");
        try {
            if (!ossClient.doesBucketExist(aliyunOssConfig.getBucketName())) {
                log.error("Bucket不存在");
            }
            ossClient.deleteObject(aliyunOssConfig.getBucketName(), fileName);
            log.info("==============>Object删除成功：" + fileName);
            return true;
        } catch (Exception e) {
            log.error("oss删除异常", e);
        } finally {
            //关闭
            ossClient.shutdown();
        }
        return false;
    }


    /**
     * @Description: 查询文件名列表
     * @Author: qy
     * @param filePath: bucket下的文件路径，例如： filePath/
     **/
    public List<String> getObjectList(String filePath) {
        OSSClient ossClient = (OSSClient) getOssClient();
        String datePath = format.format(new Date());
        List<String> listRe = new ArrayList<>();
        try {
            log.info("===========>查询文件名列表");
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(aliyunOssConfig.getBucketName());
            // listObjectsRequest.setPrefix(aliyunOssConfig.getFilePath() + "/" + datePath + "/");
            listObjectsRequest.setPrefix(filePath);
            ObjectListing list = ossClient.listObjects(listObjectsRequest);
            // OSSobjectSummary下包含目录下所有的文件，不包含子目录
            for (OSSObjectSummary objectSummary : list.getObjectSummaries()) {
                listRe.add(objectSummary.getKey());
            }
            return listRe;
        } catch (Exception ex) {
            log.error("==========>查询列表失败", ex);
            return new ArrayList<>();
        }
    }

    /**
     * @Description: 获取OSS上传Token
     * @param fileName: aaa.txt
     **/
    public Map<String, String> getUploadToken(String filePath,String fileName) {
        OSSClient ossClient = (OSSClient) getOssClient();
        String datePath = format.format(new Date());
        try{
            log.info("===========>获取uploadToken");
            String dir = filePath+ "/" + datePath +"/";
            long currentTime = System.currentTimeMillis();
            String key = dir + fileName.split("\\.")[0] + "-" + currentTime + "." + fileName.split("\\.")[1];
            long expireEndTime = currentTime + EXPIRTATION_TIME;
            Date expiration = new Date(expireEndTime);

            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, CONTENT_LENGTH_RANGE_MIN, CONTENT_LENGTH_RANGE_MAX);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            //JSONObject jasonCallback = new JSONObject();
            //你的回调URL
            //jasonCallback.put("callbackUrl", String.format("%s/callback/imgUploadSuccess", apiDomain));
            //阿里云回调返回参数
            //jasonCallback.put("callbackBody", "id=" + id + "&filename=${object}&type=" + type + "&etag=${etag}&size=${size}&height=${imageInfo.height}&width=${imageInfo.width}&mimeType=${mimeType}");
            //asonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            //String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            Map<String, String> res = new HashMap<>();
            //res.put("callback", base64CallbackBody);
            res.put("key", key); //dir + fileName (上传Object的名称。)
            res.put("OSSAccessKeyId", aliyunOssConfig.getAccessKeyId());
            res.put("policy", encodedPolicy); //Policy规定了请求表单域的合法性。
            res.put("signature", postSignature); //根据AccessKey Secret和Policy计算的签名信息，OSS验证该签名信息从而验证该Post请求的合法性。
            res.put("dir", dir); //前缀
            res.put("uploadUrl", "https://" + aliyunOssConfig.getBucketName() + "." + aliyunOssConfig.getEndpoint()); // "https://" + bucketname + '.' + endpoint;  (前端请求oss服务路径)
            res.put("expire", String.valueOf(expireEndTime / 1000));
            res.put("success_action_status", "200");
            log.debug("res:{}",res);
            return res;
        }
        catch (Exception e){
            log.error("==========>获取uploadToken失败", e);
        }
        finally {
            ossClient.shutdown();
        }
        return null;
    }
}
