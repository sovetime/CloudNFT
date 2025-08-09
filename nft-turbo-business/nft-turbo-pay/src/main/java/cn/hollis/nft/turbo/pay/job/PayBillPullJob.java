package cn.hollis.nft.turbo.pay.job;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.pay.domain.entity.WechatTransaction;
import cn.hollis.nft.turbo.pay.domain.service.WechatTransactionService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.DownloadBillChannelRequest;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.TradeBillChannelRequest;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillDownloadChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.constant.WxBillType;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.utils.WeChatUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.COMMON_DATE_PATTERN;

@Component
@Slf4j
public class PayBillPullJob {

    private static final String NO_STATEMENT_EXIST = "NO_STATEMENT_EXIST";

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    @Autowired
    private WechatTransactionService wechatTransactionService;

    @XxlJob("wxPaySuccessTradeBillPullJob")
    public ReturnT<String> wxPaySuccessTradeBillPullJob() {

        //从xxl-job的配置获取时间参数
        String billDate = XxlJobHelper.getJobParam();
        if (StringUtils.isEmpty(billDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_PATTERN);
            //默认查询上一日的账单
            billDate = sdf.format(DateUtils.addDays(new Date(), -1));
        }

        TradeBillChannelRequest tradeBillChannelRequest = new TradeBillChannelRequest();
        tradeBillChannelRequest.setBillType(WxBillType.SUCCESS.name());
        tradeBillChannelRequest.setBillDate(billDate);

        BillChannelResponse billChannelResponse = payChannelServiceFactory.get(PayChannel.WECHAT).tradeBill(tradeBillChannelRequest);
        if (!billChannelResponse.getSuccess()) {
            log.error("tradeBill get failed , responseCode : {}", billChannelResponse.getResponseCode());
            if (billChannelResponse.getResponseCode().equals(NO_STATEMENT_EXIST)) {
                //如果前一天无交易，则直接返回成功
                return ReturnT.SUCCESS;
            }
            return ReturnT.FAIL;
        }

        //从billChannelResponse的downloadUrl中解析出token参数
        String downloadUrl = billChannelResponse.getDownloadUrl();
        String token = UrlBuilder.ofHttp(downloadUrl, CharsetUtil.CHARSET_UTF_8).getQuery().get("token").toString();

        //下载账单并解析
        DownloadBillChannelRequest downloadBillChannelRequest = new DownloadBillChannelRequest(token);
        BillDownloadChannelResponse billDownloadChannelResponse = payChannelServiceFactory.get(PayChannel.WECHAT).downloadBill(downloadBillChannelRequest);

        if (!billDownloadChannelResponse.getSuccess()) {
            log.error("tradeBill download failed , responseCode : {}", billDownloadChannelResponse.getResponseCode());
            return ReturnT.FAIL;
        }

        List<WechatTransaction> transactions = WeChatUtil.parseWechatTradeBillData(billDownloadChannelResponse.getFile(),WxBillType.SUCCESS.name());

        //针对transactions分批批量插入数据库，每批不超过200条
        boolean result = wechatTransactionService.saveOrUpdateBatch(transactions, 200);
        if (!result) {
            log.error("wechatTransaction save failed , billDate : {}", billDate);
        }
        return result ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @XxlJob("wxPayRefundTradeBillPullJob")
    public ReturnT<String> wxPayRefundTradeBillPullJob() {

        //从xxl-job的配置获取时间参数
        String billDate = XxlJobHelper.getJobParam();
        if (billDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_PATTERN);
            //默认查询上一日的账单
            billDate = sdf.format(DateUtils.addDays(new Date(), -1));
        }

        TradeBillChannelRequest tradeBillChannelRequest = new TradeBillChannelRequest();
        tradeBillChannelRequest.setBillType(WxBillType.REFUND.name());
        tradeBillChannelRequest.setBillDate(billDate);

        BillChannelResponse billChannelResponse = payChannelServiceFactory.get(PayChannel.WECHAT).tradeBill(tradeBillChannelRequest);
        if (!billChannelResponse.getSuccess()) {
            log.error("tradeBill get failed , responseCode : {}", billChannelResponse.getResponseCode());
            return ReturnT.FAIL;
        }

        //从billChannelResponse的downloadUrl中解析出token参数
        String downloadUrl = billChannelResponse.getDownloadUrl();
        String token = UrlBuilder.ofHttp(downloadUrl, CharsetUtil.CHARSET_UTF_8).getQuery().get("token").toString();

        //下载账单并解析
        DownloadBillChannelRequest downloadBillChannelRequest = new DownloadBillChannelRequest(token);
        BillDownloadChannelResponse billDownloadChannelResponse = payChannelServiceFactory.get(PayChannel.WECHAT).downloadBill(downloadBillChannelRequest);

        if (!billDownloadChannelResponse.getSuccess()) {
            log.error("tradeBill download failed , responseCode : {}", billDownloadChannelResponse.getResponseCode());
            return ReturnT.FAIL;
        }

        List<WechatTransaction> transactions = WeChatUtil.parseWechatTradeBillData(billDownloadChannelResponse.getFile(),WxBillType.REFUND.name());

        //针对transactions分批批量插入数据库，每批不超过200条
        boolean result = wechatTransactionService.saveOrUpdateBatch(transactions, 200);
        if (!result) {
            log.error("wechatTransaction save failed , billDate : {}", billDate);
        }
        return result ? ReturnT.SUCCESS : ReturnT.FAIL;
    }
}
