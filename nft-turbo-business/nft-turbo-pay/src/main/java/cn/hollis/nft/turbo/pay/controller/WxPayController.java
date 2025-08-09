package cn.hollis.nft.turbo.pay.controller;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.base.utils.MoneyUtils;
import cn.hollis.nft.turbo.pay.application.service.PayApplicationService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.PayChannelRequest;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.response.WxPayChannelResponse;
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

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.HTTP_SERVER_ERROR_CODE;
import static cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.impl.MockPayChannelServiceImpl.context;

/**
 * 微信支付回调入口
 *
 * @author hollis
 */
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

    @RequestMapping("/nativePay")
    @ResponseBody
    public String nativePay() {
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.WECHAT);
        PayChannelRequest payChannelRequest = new PayChannelRequest();
        payChannelRequest.setOrderId(PayKit.generateStr());
        payChannelRequest.setAmount(1L);
        payChannelRequest.setDescription("支付测试");
        payChannelRequest.setAttach("支付测试");
        WxPayChannelResponse response = (WxPayChannelResponse) wxPayChannelService.pay(payChannelRequest);
        return response.getPayUrl();
    }

    @RequestMapping(value = "/payNotify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.WECHAT);
        boolean result = wxPayChannelService.notify(request, response);
        if (!result) {
            response.setStatus(HTTP_SERVER_ERROR_CODE);
        }
    }

    @RequestMapping(value = "/payNotifyMock", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void payNotifyMock(String payOrderId, String paidAmount) {
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.MOCK);

        Map<String, Serializable> params = new HashMap<>(12);
        params.put("payOrderId", payOrderId);
        params.put("paidAmount", MoneyUtils.yuanToCent(new BigDecimal(paidAmount)));
        context.set(params);

        boolean result = wxPayChannelService.notify(null, null);

        Assert.isTrue(result, "支付通知失败");
    }

    @RequestMapping(value = "/refundNotify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void refundNotify(HttpServletRequest request, HttpServletResponse response) {
        PayChannelService wxPayChannelService = payChannelServiceFactory.get(PayChannel.WECHAT);
        boolean result = wxPayChannelService.refundNotify(request, response);
        if (!result) {
            response.setStatus(HTTP_SERVER_ERROR_CODE);
        }
    }
}
