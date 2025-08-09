package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.service.impl;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.base.utils.MoneyUtils;
import cn.hollis.nft.turbo.pay.application.service.PayApplicationService;
import cn.hollis.nft.turbo.pay.domain.event.PaySuccessEvent;
import cn.hollis.nft.turbo.pay.domain.event.RefundSuccessEvent;
import cn.hollis.nft.turbo.pay.domain.service.PayOrderService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.*;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillDownloadChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.PayChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.utils.HttpKit;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayBean;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayNotifyEntity;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity.WxRefundNotifyEntity;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.response.WxPayChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.response.WxPayRefundBody;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.response.WxRefundChannelResponse;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.AuthTypeEnum;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.RefundAmount;
import com.ijpay.wxpay.model.v3.RefundModel;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.HTTP_SERVER_ERROR_CODE;
import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.HTTP_SUCCESS_CODE;
import static cn.hollis.nft.turbo.base.response.ResponseCode.SUCCESS;
import static cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.constant.WxTradeState.PAYERROR;

/**
 * @author hollis
 */
@Service("wechatPayChannelService")
@Slf4j
public class WxPayChannelServiceImpl implements PayChannelService {
    @Autowired
    WxPayBean wxPayBean;

    @Autowired
    private PayApplicationService payApplicationService;

    @Autowired
    private PayOrderService payOrderService;

    String serialNo;

    @Override
    public PayChannelResponse pay(PayChannelRequest payChannelRequest) {
        WxPayChannelResponse resp = new WxPayChannelResponse();

        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(payChannelRequest.getExpireTime());

            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(wxPayBean.getAppId())
                    .setMchid(wxPayBean.getMchId())
                    .setDescription(payChannelRequest.getDescription())
                    .setOut_trade_no(payChannelRequest.getOrderId())
                    .setTime_expire(timeExpire)
                    //附加数据，暂时先设置为与商品信息一致
                    .setAttach(payChannelRequest.getAttach())
                    .setNotify_url(wxPayBean.getDomain().concat("/wxPay/payNotify"))
                    .setAmount(new Amount().setTotal(Integer.parseInt(String.valueOf(payChannelRequest.getAmount()))));

            log.info("request {}", JSONUtil.toJsonStr(unifiedOrderModel));
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.NATIVE_PAY.toString(),
                    wxPayBean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayBean.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel),
                    AuthTypeEnum.RSA.getCode()
            );
            log.info("response {}", response);
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayBean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            String body = response.getBody();
            Map bodyMap = JSON.parseObject(body, Map.class);
            resp.setPayUrl(bodyMap.get("code_url").toString());
            resp.setSuccess(true);
            return resp;
        } catch (Exception e) {
            log.error("pay error ", e);
            resp.setSuccess(false);
            return resp;
        }
    }

    @Override
    public boolean notify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);

            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wxPayBean.getApiKey3(), wxPayBean.getPlatformCertPath());

            log.info("支付通知明文 {}", plainText);
            if (StrUtil.isEmpty(plainText)) {
                response.setStatus(HTTP_SERVER_ERROR_CODE);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            } else {
                WxPayNotifyEntity wxPayNotifyEntity = JSON.parseObject(plainText, WxPayNotifyEntity.class);
                if (wxPayNotifyEntity.getTradeState().equals(SUCCESS.name())) {
                    PaySuccessEvent paySuccessEvent = new PaySuccessEvent();
                    paySuccessEvent.setChannelStreamId(wxPayNotifyEntity.getTransactionId());
                    paySuccessEvent.setPaidAmount(MoneyUtils.centToYuan(Long.valueOf(wxPayNotifyEntity.getAmount().getTotal())));
                    paySuccessEvent.setPayOrderId(wxPayNotifyEntity.getOutTradeNo());
                    paySuccessEvent.setPaySucceedTime(DateUtil.parseUTC(wxPayNotifyEntity.getSuccessTime()));
                    paySuccessEvent.setPayChannel(PayChannel.WECHAT);

                    boolean paySuccessResult = payApplicationService.paySuccess(paySuccessEvent);

                    if (paySuccessResult) {
                        response.setStatus(HTTP_SUCCESS_CODE);
                        map.put("code", SUCCESS.name());
                        map.put("message", SUCCESS.name());
                    } else {
                        response.setStatus(HTTP_SERVER_ERROR_CODE);
                        map.put("code", "ERROR");
                        map.put("message", "内部处理失败");
                    }
                } else if (wxPayNotifyEntity.getTradeState().equals(PAYERROR.name())) {
                    //这里只针对明确的支付失败做处理，其他状态均不涉及或不处理，等最终状态通知
                    boolean payFailedResult = payApplicationService.payFailed(wxPayNotifyEntity.getOutTradeNo());

                    if (payFailedResult) {
                        response.setStatus(HTTP_SUCCESS_CODE);
                        map.put("code", SUCCESS.name());
                        map.put("message", SUCCESS.name());
                    } else {
                        response.setStatus(HTTP_SERVER_ERROR_CODE);
                        map.put("code", "ERROR");
                        map.put("message", "内部处理失败");
                    }
                }
            }

            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            log.error("nofity error", e);
            return false;
        }
        return true;
    }

    @Override
    public WxRefundChannelResponse refund(RefundChannelRequest refundChannelRequest) {
        WxRefundChannelResponse resp = new WxRefundChannelResponse();

        try {
            RefundModel refundModel = new RefundModel()
                    .setOut_refund_no(refundChannelRequest.getRefundOrderId())
                    .setReason(refundChannelRequest.getRefundReason())
                    .setNotify_url(wxPayBean.getDomain().concat("/wxPay/refundNotify"))
                    .setOut_trade_no(refundChannelRequest.getPayOrderId())
                    .setTransaction_id(refundChannelRequest.getPayChannelStreamId())
                    .setAmount(new RefundAmount().setRefund(refundChannelRequest.getRefundAmount().intValue()).setTotal(refundChannelRequest.getPaidAmount().intValue()).setCurrency("CNY"));

            log.info("refund param {}", JSONUtil.toJsonStr(refundModel));
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.REFUND.toString(),
                    wxPayBean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayBean.getKeyPath(),
                    JSONUtil.toJsonStr(refundModel)
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayBean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            log.info("refund response {}", response);

            if (verifySignature) {
                log.info("refund body {}", JSON.toJSONString(response.getBody()));
                if (response.getStatus() != HTTP_SUCCESS_CODE) {
                    resp.setSuccess(false);
                    Map<String, String> bodyMap = JSON.parseObject(response.getBody(), Map.class);
                    resp.setResponseCode(bodyMap.get("code"));
                    resp.setResponseMessage(bodyMap.get("message"));
                }
                WxPayRefundBody wxPayRefundBody = JSON.parseObject(response.getBody(), WxPayRefundBody.class);
                resp.setWxPayRefundBody(wxPayRefundBody);
                resp.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("pay error ", e);
            resp.setSuccess(false);
        }
        return resp;
    }


    /**
     * 退款通知
     */
    @Override
    public boolean refundNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(12);
        try {
            String result = HttpKit.readData(request);
            log.info("退款通知=" + result);
            Map<String, String> params = WxPayKit.xmlToMap(result);

            String returnCode = params.get("return_code");
            // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
            if (WxPayKit.codeIsOk(returnCode)) {
                String reqInfo = params.get("req_info");
                String decryptData = WxPayKit.decryptData(reqInfo, WxPayApiConfigKit.getWxPayApiConfig().getPartnerKey());
                log.info("退款通知解密后的数据=" + decryptData);

                if (StrUtil.isEmpty(decryptData)) {
                    response.setStatus(HTTP_SERVER_ERROR_CODE);
                    map.put("code", "ERROR");
                    map.put("message", "签名错误");
                } else {
                    WxRefundNotifyEntity wxRefundNotifyEntity = JSON.parseObject(decryptData, WxRefundNotifyEntity.class);

                    RefundSuccessEvent refundSuccessEvent = new RefundSuccessEvent();
                    refundSuccessEvent.setChannelStreamId(wxRefundNotifyEntity.getRefundId());
                    refundSuccessEvent.setRefundedAmount(MoneyUtils.centToYuan(Long.valueOf(wxRefundNotifyEntity.getSettlementRefundFee())));
                    refundSuccessEvent.setPayOrderId(wxRefundNotifyEntity.getOutTradeNo());
                    refundSuccessEvent.setRefundOrderId(wxRefundNotifyEntity.getOutRefundNo());
                    refundSuccessEvent.setRefundedTime(DateUtil.parseUTC(wxRefundNotifyEntity.getSuccessTime()));
                    refundSuccessEvent.setRefundChannel(PayChannel.WECHAT);

                    boolean refundSuccessResult = payApplicationService.refundSuccess(refundSuccessEvent);

                    if (refundSuccessResult) {
                        response.setStatus(HTTP_SUCCESS_CODE);
                        map.put("code", SUCCESS.name());
                        map.put("message", SUCCESS.name());
                    } else {
                        response.setStatus(500);
                        map.put("code", "ERROR");
                        map.put("message", "内部处理失败");
                    }
                }

                response.setHeader("Content-type", ContentType.JSON.toString());
                response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
                response.flushBuffer();
            }

        } catch (Exception e) {
            log.error("refund notify error", e);
            return false;
        }
        return true;
    }

    @Override
    public BillChannelResponse tradeBill(TradeBillChannelRequest billChannelRequest) {
        BillChannelResponse resp = new BillChannelResponse();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("bill_date", billChannelRequest.getBillDate());
            params.put("bill_type", StringUtils.isBlank(billChannelRequest.getBillType()) ? "ALL" : billChannelRequest.getBillType());
            params.put("tar_type", StringUtils.isBlank(billChannelRequest.getTarType()) ? "GZIP" : billChannelRequest.getTarType());

            log.info("request {}", JSONUtil.toJsonStr(params));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.TRADE_BILL.toString(),
                    wxPayBean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayBean.getKeyPath(),
                    params
            );

            log.info("response {}", response);
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayBean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            String body = response.getBody();
            Map bodyMap = JSON.parseObject(body, Map.class);


            if(response.getStatus() == 200){
                resp.setDownloadUrl(bodyMap.get("download_url").toString());
                resp.setHashType(bodyMap.get("hash_type").toString());
                resp.setHashValue(bodyMap.get("hash_value").toString());
                resp.setSuccess(true);
                return resp;
            }else{
                resp.setSuccess(false);
                resp.setResponseCode(bodyMap.get("code").toString());
                resp.setResponseMessage(bodyMap.get("message").toString());
                return resp;
            }
        } catch (Exception e) {
            log.error("bill error ", e);
            resp.setSuccess(false);
            return resp;
        }
    }

    @Override
    public BillChannelResponse fundBill(FundBillChannelRequest billChannelRequest) {
        BillChannelResponse resp = new BillChannelResponse();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("bill_date", billChannelRequest.getBillDate());
            params.put("account_type", StringUtils.isBlank(billChannelRequest.getAccountType()) ? "BASIC" : billChannelRequest.getAccountType());

            log.info("request {}", JSONUtil.toJsonStr(params));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.FUND_FLOW_BILL.toString(),
                    wxPayBean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayBean.getKeyPath(),
                    params
            );

            log.info("response {}", response);
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayBean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            String body = response.getBody();
            Map bodyMap = JSON.parseObject(body, Map.class);
            resp.setDownloadUrl(bodyMap.get("download_url").toString());
            resp.setHashType(bodyMap.get("hash_type").toString());
            resp.setHashValue(bodyMap.get("hash_value").toString());
            resp.setSuccess(true);
            return resp;
        } catch (Exception e) {
            log.error("bill error ", e);
            resp.setSuccess(false);
            return resp;
        }
    }

    @Override
    public BillDownloadChannelResponse downloadBill(DownloadBillChannelRequest request) {
        BillDownloadChannelResponse resp = new BillDownloadChannelResponse();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("token", request.getToken());

            log.info("request {}", JSONUtil.toJsonStr(params));

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.toString(),
                    BasePayApiEnum.BILL_DOWNLOAD.toString(),
                    wxPayBean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayBean.getKeyPath(),
                    params
            );
            log.info("response {}", response);
            String body = response.getBody();
            resp.setFile(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            resp.setSuccess(true);
            return resp;
        } catch (Exception e) {
            log.error("bill download error ", e);
            resp.setSuccess(false);
            return resp;
        }
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
}
