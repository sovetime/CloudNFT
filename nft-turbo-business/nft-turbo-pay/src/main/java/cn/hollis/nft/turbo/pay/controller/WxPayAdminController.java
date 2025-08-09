package cn.hollis.nft.turbo.pay.controller;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayBean;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.AuthTypeEnum;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.CertAlgorithmTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hollis
 */
@Slf4j
@Controller
@RequestMapping("/wxPayAdmin")
public class WxPayAdminController {
    @Resource
    WxPayBean wxPayBean;

    String serialNo;

    @GetMapping("/test")
    @ResponseBody
    public WxPayBean test() {
        return wxPayBean;
    }

    private String getSerialNumber() {
        if (StrUtil.isEmpty(serialNo)) {
            // 获取证书序列号
            X509Certificate certificate = PayKit.getCertificate(wxPayBean.getCertPath());
            if (null != certificate) {
                serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
                // 提前两天检查证书是否有效
                boolean isValid = PayKit.checkCertificateIsValid(certificate, wxPayBean.getMchId(), -2);
                log.info("cert is valid {} effectiveTime {}", isValid, DateUtil.format(certificate.getNotAfter(), DatePattern.NORM_DATETIME_PATTERN));
            }
        }
        System.out.println("serialNo:" + serialNo);
        return serialNo;
    }

    @RequestMapping("/fetchPlatformCert")
    @ResponseBody
    public String fetchPlatformCert() {
        // 获取平台证书列表
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.toString(),
                    CertAlgorithmTypeEnum.getCertSuffixUrl(CertAlgorithmTypeEnum.RSA.getCode()),
                    wxPayBean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayBean.getKeyPath(),
                    "",
                    AuthTypeEnum.RSA.getCode()
            );
            Map<String, List<String>> headers = response.getHeaders();
            log.info("headers: {}", headers);
            String timestamp = response.getHeader("Wechatpay-Timestamp");
            String nonceStr = response.getHeader("Wechatpay-Nonce");
            String serialNumber = response.getHeader("Wechatpay-Serial");
            String signature = response.getHeader("Wechatpay-Signature");

            String body = response.getBody();
            int status = response.getStatus();

            log.info("serialNumber: {}", serialNumber);
            log.info("status: {}", status);
            log.info("body: {}", body);
            int isOk = 200;
            if (status == isOk) {
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                // 默认认为只有一个平台证书
                JSONObject encryptObject = dataArray.getJSONObject(0);
                JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
                String associatedData = encryptCertificate.getStr("associated_data");
                String cipherText = encryptCertificate.getStr("ciphertext");
                String nonce = encryptCertificate.getStr("nonce");
                String algorithm = encryptCertificate.getStr("algorithm");
                String serialNo = encryptObject.getStr("serial_no");
                final String platSerialNo = savePlatformCert(associatedData, nonce, cipherText, algorithm, wxPayBean.getPlatformCertPath());
                log.info("platSerialNo: {} serialNo: {}", platSerialNo, serialNo);
                // 根据证书序列号查询对应的证书来验证签名结果
                boolean verifySignature = WxPayKit.verifySignature(response, wxPayBean.getPlatformCertPath());
                log.info("verifySignature:{}", verifySignature);
            }
            return body;
        } catch (Exception e) {
            log.error("fetchPlatformCert error", e);
            return null;
        }
    }

    private String savePlatformCert(String associatedData, String nonce, String cipherText, String algorithm, String certPath) {
        try {
            String key3 = wxPayBean.getApiKey3();
            String publicKey;
            if (StrUtil.equals(algorithm, AuthTypeEnum.SM2.getPlatformCertAlgorithm())) {
                publicKey = PayKit.sm4DecryptToString(key3, cipherText, nonce, associatedData);
            } else {
                AesUtil aesUtil = new AesUtil(wxPayBean.getApiKey3().getBytes(StandardCharsets.UTF_8));
                // 平台证书密文解密
                // encrypt_certificate 中的  associated_data nonce  ciphertext
                publicKey = aesUtil.decryptToString(
                        associatedData.getBytes(StandardCharsets.UTF_8),
                        nonce.getBytes(StandardCharsets.UTF_8),
                        cipherText
                );
            }
            if (StrUtil.isNotEmpty(publicKey)) {
                // 保存证书
                FileWriter writer = new FileWriter(certPath);
                writer.write(publicKey);
                // 获取平台证书序列号
                X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
                return certificate.getSerialNumber().toString(16).toUpperCase();
            }
            return "";
        } catch (Exception e) {
            log.error("savePlatformCert error", e);
            return e.getMessage();
        }
    }
}
