package cn.hollis.nft.turbo.pay.job;

import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import cn.hollis.nft.turbo.pay.domain.entity.WechatTransaction;
import cn.hollis.nft.turbo.pay.domain.service.PayOrderService;
import cn.hollis.nft.turbo.pay.domain.service.WechatTransactionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.COMMON_DATE_PATTERN;


//支付一致性检查任务
@Component
public class PayDetailCheckJob {

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private WechatTransactionService wechatTransactionService;


    private static final int PAGE_SIZE = 300;

    @XxlJob("payDetailCheckJob")
    public ReturnT<String> execute() {

        //从xxl-job的配置获取时间参数
        Date billDate;
        if (StringUtils.isNotBlank(XxlJobHelper.getJobParam())) {
            SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_PATTERN);
            //默认查询上一日的账单
            try {
                billDate = sdf.parse(XxlJobHelper.getJobParam());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            billDate = DateUtils.addDays(new Date(), -1);
        }

        int currentPage = 1;

        Page<PayOrder> payOrders = payOrderService.pageQuerySucceedOrders(PAGE_SIZE, currentPage, billDate);

        payOrders.getRecords().forEach(payOrder -> doExecute(payOrder, billDate));

        while (payOrders.getCurrent() < payOrders.getPages()) {
            currentPage++;
            payOrders = payOrderService.pageQuerySucceedOrders(PAGE_SIZE, currentPage, billDate);
            payOrders.getRecords().forEach(payOrder -> doExecute(payOrder, billDate));
        }

        return ReturnT.SUCCESS;
    }

    /**
     * todo 未完成
     * @param payOrder
     * @param billDate
     */
    private void doExecute(PayOrder payOrder, Date billDate) {
        WechatTransaction wechatTransaction = wechatTransactionService.queryByMchOrderNo(payOrder.getPayOrderId());
        boolean checkSuccess = false;
        if (wechatTransaction == null) {

        }

        if (wechatTransaction.getStatus() != "") {

        }

        if (wechatTransaction.getAmount().compareTo(payOrder.getPaidAmount()) != 0) {

        }

        //如果billDate是2025-09-01，则dailyCutTimeStart为2025-09-01 00:00:00，dailyCutTimeEnd为2025-09-02 00:00:00
        Date dailyCutTimeStart = DateUtils.truncate(billDate, Calendar.DATE);
        Date dailyCutTimeEnd = DateUtils.truncate(DateUtils.addDays(billDate, 1), Calendar.DATE);

        if (!checkSuccess) {
            //支付单发生时间为2025-08-31 23:55:00 至 2025-09-01 00:05:00之间，则可能是日切时间点附近的数据，多个系统因为发生时间不一致，可能会导致很多误报。所以需要特殊处理
            if (payOrder.getPaySucceedTime().compareTo(DateUtils.addMinutes(dailyCutTimeStart,-5)) > 0 && payOrder.getPaySucceedTime().compareTo(DateUtils.addMinutes(dailyCutTimeStart,5)) < 0 ){

            }

            if (payOrder.getPaySucceedTime().compareTo(DateUtils.addMinutes(dailyCutTimeEnd,-5)) > 0 && payOrder.getPaySucceedTime().compareTo(DateUtils.addMinutes(dailyCutTimeEnd,5)) < 0 ){

            }
        }

    }
}
