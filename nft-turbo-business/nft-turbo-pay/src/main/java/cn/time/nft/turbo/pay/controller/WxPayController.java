package cn.time.nft.turbo.pay.controller;

import cn.time.nft.turbo.api.pay.constant.PayChannel;
import cn.time.nft.turbo.base.utils.MoneyUtils;
import cn.time.nft.turbo.pay.application.service.PayApplicationService;
import cn.time.nft.turbo.pay.infrastructure.channel.common.request.PayChannelRequest;
import cn.time.nft.turbo.pay.infrastructure.channel.common.service.PayChannelService;
import cn.time.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.time.nft.turbo.pay.infrastructure.channel.wechat.response.WxPayChannelResponse;
import com.ijpay.core.kit.PayKit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static cn.time.nft.turbo.api.common.constant.CommonConstant.HTTP_SERVER_ERROR_CODE;
import static cn.time.nft.turbo.pay.infrastructure.channel.common.service.impl.MockPayChannelServiceImpl.context;


// 微信支付+回调
@Slf4j
@Controller
@RequestMapping("/wxPay")
public class WxPayController {

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    @Autowired
    private PayApplicationService payApplicationService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(String orderId, String paidAmount) {
        payApplicationService.test();
        return "test";
    }

    // 微信支付
    @RequestMapping("/nativePay")
    @ResponseBody
    public String nativePay() {
        //获取支付渠道服务
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.WECHAT);
        PayChannelRequest payChannelRequest = new PayChannelRequest();
        payChannelRequest.setOrderId(PayKit.generateStr());
        payChannelRequest.setAmount(1L);
        payChannelRequest.setDescription("支付测试");
        payChannelRequest.setAttach("支付测试");
        //支付
        WxPayChannelResponse response = (WxPayChannelResponse) wxPayChannelService.pay(payChannelRequest);
        return response.getPayUrl();
    }

    // 微信支付回调
    @RequestMapping(value = "/payNotify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        //获取支付渠道服务
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.WECHAT);
        //支付结果回调
        boolean result = wxPayChannelService.notify(request, response);
        if (!result) {
            response.setStatus(HTTP_SERVER_ERROR_CODE);
        }
    }

    // 模拟支付回调
    @RequestMapping(value = "/payNotifyMock", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void payNotifyMock(String payOrderId, String paidAmount) {
        //获取支付渠道服务
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.MOCK);

        Map<String, Serializable> params = new HashMap<>(12);
        params.put("payOrderId", payOrderId);
        params.put("paidAmount", MoneyUtils.yuanToCent(new BigDecimal(paidAmount)));
        context.set(params);

        //支付结果回调
        boolean result = wxPayChannelService.notify(null, null);

        Assert.isTrue(result, "支付通知失败");
    }

    // 微信退款回调
    @RequestMapping(value = "/refundNotify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void refundNotify(HttpServletRequest request, HttpServletResponse response) {
        //获取支付渠道服务
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.WECHAT);
        //退款结果回调
        boolean result = wxPayChannelService.refundNotify(request, response);
        if (!result) {
            response.setStatus(HTTP_SERVER_ERROR_CODE);
        }
    }
}
