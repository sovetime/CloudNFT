package cn.hollis.nft.turbo.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * oss 服务
 * @author hollis
 */
@Slf4j
@Setter
public class OssServiceImpl implements FileService {

    private String bucket;

    private String endPoint;

    private String accessKey;

    private String accessSecret;

    @Override
    public boolean upload(String path, InputStream fileStream) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = endPoint;
        // 从环境变量中获取RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = accessKey;
        String accessKeySecret = accessSecret;
        // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

        // 填写Bucket名称，例如examplebucket。
        String bucketName = bucket;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = path;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        boolean uploadRes = false;
        try {

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, fileStream);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            if (StringUtils.isNotBlank(result.getRequestId())) {
                uploadRes = true;
            }
        } catch (Exception e) {
            log.error("OssUtil upload error,path=" + path, e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return uploadRes;
    }

}
