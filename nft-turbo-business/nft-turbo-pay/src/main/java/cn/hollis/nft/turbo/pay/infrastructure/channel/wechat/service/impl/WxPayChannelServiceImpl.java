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


@Service("wechatPayChannelService")
@Slf4j
public class WxPayChannelServiceImpl implements PayChannelService {

    //微信支付bean
    @Autowired
    WxPayBean wxPayBean;


    @Autowired
    private PayApplicationService payApplicationService;

    @Autowired
    private PayOrderService payOrderService;

    String serialNo;

    //发起支付请求
    //创建微信Native支付订单，生成支付二维码URL
    @Override
    public PayChannelResponse pay(PayChannelRequest payChannelRequest) {
        //创建微信支付渠道响应
        WxPayChannelResponse resp = new WxPayChannelResponse();

        try {
            // 将过期时间转换为微信支付API要求的时区格式
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(payChannelRequest.getExpireTime());

            // 构建微信统一下单模型
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(wxPayBean.getAppId())                    // 应用ID
                    .setMchid(wxPayBean.getMchId())                    // 商户号
                    .setDescription(payChannelRequest.getDescription()) // 商品描述
                    .setOut_trade_no(payChannelRequest.getOrderId())   // 商户订单号
                    .setTime_expire(timeExpire)                        // 订单过期时间
                    .setAttach(payChannelRequest.getAttach())          // 附加数据，原样返回
                    .setNotify_url(wxPayBean.getDomain().concat("/wxPay/payNotify")) // 支付结果通知URL
                    .setAmount(new Amount().setTotal(Integer.parseInt(
                            String.valueOf(payChannelRequest.getAmount())))); // 订单金额（分）

            log.info("request {}", JSONUtil.toJsonStr(unifiedOrderModel));
            // 调用微信支付API创建Native支付订单
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

            // 验证微信支付返回的签名
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

    //处理微信支付结果通知
    //接收微信支付平台发送的支付结果异步通知，验证签名并处理支付成功或失败的业务逻辑
    @Override
    public boolean notify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(12);
        try {
            // 获取微信支付通知的签名验证所需参数
            String timestamp = request.getHeader("Wechatpay-Timestamp");// 时间戳
            String nonce = request.getHeader("Wechatpay-Nonce");        // 随机字符串
            String serialNo = request.getHeader("Wechatpay-Serial");    // 证书序列号
            String signature = request.getHeader("Wechatpay-Signature");// 签名
            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);

            // 读取请求体中的加密数据
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);

            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            // 验证通知签名并解密数据
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wxPayBean.getApiKey3(), wxPayBean.getPlatformCertPath());

            log.info("支付通知明文 {}", plainText);
            if (StrUtil.isEmpty(plainText)) {
                // 签名验证失败
                response.setStatus(HTTP_SERVER_ERROR_CODE);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            } else {
                WxPayNotifyEntity wxPayNotifyEntity = JSON.parseObject(plainText, WxPayNotifyEntity.class);
                if (wxPayNotifyEntity.getTradeState().equals(SUCCESS.name())) {
                    // 支付成功处理
                    PaySuccessEvent paySuccessEvent = new PaySuccessEvent();
                    paySuccessEvent.setChannelStreamId(wxPayNotifyEntity.getTransactionId());
                    paySuccessEvent.setPaidAmount(MoneyUtils.centToYuan(Long.valueOf(wxPayNotifyEntity.getAmount().getTotal())));
                    paySuccessEvent.setPayOrderId(wxPayNotifyEntity.getOutTradeNo());
                    paySuccessEvent.setPaySucceedTime(DateUtil.parseUTC(wxPayNotifyEntity.getSuccessTime()));
                    paySuccessEvent.setPayChannel(PayChannel.WECHAT);

                    //顶顶那支付逻辑
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

            // 返回处理结果给微信支付平台
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            log.error("nofity error", e);
            return false;
        }
        return true;
    }

    //申请退款
    //向微信支付平台发起退款申请
    @Override
    public WxRefundChannelResponse refund(RefundChannelRequest refundChannelRequest) {
        WxRefundChannelResponse resp = new WxRefundChannelResponse();

        try {
            // 构建退款请求模型
            RefundModel refundModel = new RefundModel()
                    .setOut_refund_no(refundChannelRequest.getRefundOrderId())      // 商户退款单号
                    .setReason(refundChannelRequest.getRefundReason())              // 退款原因
                    .setNotify_url(wxPayBean.getDomain().concat("/wxPay/refundNotify")) // 退款结果通知URL
                    .setOut_trade_no(refundChannelRequest.getPayOrderId())          // 原商户订单号
                    .setTransaction_id(refundChannelRequest.getPayChannelStreamId()) // 微信支付订单号
                    .setAmount(new RefundAmount()
                            .setRefund(refundChannelRequest.getRefundAmount().intValue())  // 退款金额（分）
                            .setTotal(refundChannelRequest.getPaidAmount().intValue())     // 原订单金额（分）
                            .setCurrency("CNY"));                                          // 币种
            log.info("refund param {}", JSONUtil.toJsonStr(refundModel));

            //调用微信支付API申请退款
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
                    // 退款申请失败
                    resp.setSuccess(false);
                    Map<String, String> bodyMap = JSON.parseObject(response.getBody(), Map.class);
                    resp.setResponseCode(bodyMap.get("code"));
                    resp.setResponseMessage(bodyMap.get("message"));
                }

                // 解析退款响应数据
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


    //处理微信退款结果通知
    //接收微信支付平台发送的退款结果异步通知
    @Override
    public boolean refundNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(12);
        try {
            //读取退款通知数据
            String result = HttpKit.readData(request);
            log.info("退款通知=" + result);

            // 将XML格式的通知数据转换为Map
            Map<String, String> params = WxPayKit.xmlToMap(result);

            String returnCode = params.get("return_code");
            // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
            if (WxPayKit.codeIsOk(returnCode)) {
                String reqInfo = params.get("req_info");
                // 解密退款通知数据
                String decryptData = WxPayKit.decryptData(reqInfo, WxPayApiConfigKit.getWxPayApiConfig().getPartnerKey());
                log.info("退款通知解密后的数据=" + decryptData);

                if (StrUtil.isEmpty(decryptData)) {
                    // 解密退款通知数据
                    response.setStatus(HTTP_SERVER_ERROR_CODE);
                    map.put("code", "ERROR");
                    map.put("message", "签名错误");
                } else {
                    // 解析退款通知实体
                    WxRefundNotifyEntity wxRefundNotifyEntity = JSON.parseObject(decryptData, WxRefundNotifyEntity.class);

                    // 构建退款成功事件
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

    //查询交易账单
    //获取微信支付平台的交易账单下载链接
    @Override
    public BillChannelResponse tradeBill(TradeBillChannelRequest billChannelRequest) {
        BillChannelResponse resp = new BillChannelResponse();
        try {
            // 构建请求参数
            Map<String, String> params = new HashMap<>();
            params.put("bill_date", billChannelRequest.getBillDate());
            params.put("bill_type", StringUtils.isBlank(billChannelRequest.getBillType()) ? "ALL" : billChannelRequest.getBillType());
            params.put("tar_type", StringUtils.isBlank(billChannelRequest.getTarType()) ? "GZIP" : billChannelRequest.getTarType());
            log.info("request {}", JSONUtil.toJsonStr(params));

            //调用微信支付API查询交易账单
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
                // 查询成功，设置账单下载信息
                resp.setDownloadUrl(bodyMap.get("download_url").toString());
                resp.setHashType(bodyMap.get("hash_type").toString());
                resp.setHashValue(bodyMap.get("hash_value").toString());
                resp.setSuccess(true);
                return resp;
            }else{
                // 查询失败
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

    //资金账单
    //获取微信支付平台的资金流水账单下载链接
    @Override
    public BillChannelResponse fundBill(FundBillChannelRequest billChannelRequest) {
        BillChannelResponse resp = new BillChannelResponse();
        try {
            // 构建请求参数
            Map<String, String> params = new HashMap<>();
            params.put("bill_date", billChannelRequest.getBillDate());
            params.put("account_type", StringUtils.isBlank(billChannelRequest.getAccountType()) ? "BASIC" : billChannelRequest.getAccountType());

            log.info("request {}", JSONUtil.toJsonStr(params));

            // 调用微信支付API查询资金账单
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

            // 设置账单下载信息
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

    //下载账单
    //根据账单查询返回的token下载具体的账单文件内容
    @Override
    public BillDownloadChannelResponse downloadBill(DownloadBillChannelRequest request) {
        BillDownloadChannelResponse resp = new BillDownloadChannelResponse();
        try {
            // 构建请求参数
            Map<String, String> params = new HashMap<>();
            params.put("token", request.getToken());

            log.info("request {}", JSONUtil.toJsonStr(params));

            // 调用微信支付API下载账单文件
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
            // 将账单内容转换为字节流
            resp.setFile(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            resp.setSuccess(true);
            return resp;
        } catch (Exception e) {
            log.error("bill download error ", e);
            resp.setSuccess(false);
            return resp;
        }
    }

    //获取证书序列号
    //从证书文件中提取序列号，用于微信支付API调用时的身份验证
    //同时检查证书的有效性
    private String getSerialNumber() {
        if (StrUtil.isEmpty(serialNo)) {
            // 从证书路径获取X509证书对象
            X509Certificate certificate = PayKit.getCertificate(wxPayBean.getCertPath());
            if (null != certificate) {
                // 获取证书序列号并转换为16进制大写格式
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
