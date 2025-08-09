package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.utils;

import cn.hollis.nft.turbo.pay.domain.entity.WechatTransaction;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.constant.WxBillType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.COMMON_TIME_PATTERN;

public class WeChatUtil {

    public static List<WechatTransaction> parseWechatTradeBillData(InputStream inputStream, String billType) {
        List<WechatTransaction> transactions = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord record : parser) {
                if (record.size() == 0) continue;

                // 跳过表头
                if (record.get(0).contains("交易时间")) {
                    continue;
                }

                // 跳过汇总数据
                if (record.get(0).contains("总交易单数")) {
                    return transactions;
                }

                SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_PATTERN);
                WechatTransaction tx = null;
                // 创建交易记录对象
                if (billType.equals(WxBillType.REFUND.name())) {
                    tx = getWechatRefundTransaction(record, sdf);
                } else if (billType.equals(WxBillType.SUCCESS.name())) {
                    tx = getWechatSuccessTransaction(record, sdf);
                }
                transactions.add(tx);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    private static WechatTransaction getWechatRefundTransaction(CSVRecord record, SimpleDateFormat sdf) throws ParseException {
        WechatTransaction tx = new WechatTransaction();

        tx.setDate(sdf.parse(parseWechatTradeBillData(record.get(0))));
        tx.setAppId(parseWechatTradeBillData(record.get(1)));
        tx.setMchId(parseWechatTradeBillData(record.get(2)));
        tx.setSubMchId(parseWechatTradeBillData(record.get(3)));
        tx.setDeviceInfo(parseWechatTradeBillData(record.get(4)));
        tx.setWechatOrderNo(parseWechatTradeBillData(record.get(5)));
        tx.setMchOrderNo(parseWechatTradeBillData(record.get(6)));
        tx.setUserId(parseWechatTradeBillData(record.get(7)));
        tx.setType(parseWechatTradeBillData(record.get(8)));
        tx.setStatus(parseWechatTradeBillData(record.get(9)));
        tx.setBank(parseWechatTradeBillData(record.get(10)));
        tx.setCurrency(parseWechatTradeBillData(record.get(11)));
        tx.setAmount(new BigDecimal(parseWechatTradeBillData(record.get(12))));
        tx.setEnvelopeAmount(new BigDecimal(parseWechatTradeBillData(record.get(13))));
        tx.setRefundApplyTime(sdf.parse(parseWechatTradeBillData(record.get(14))));
        tx.setRefundSuccessTime(sdf.parse(parseWechatTradeBillData(record.get(15))));
        tx.setWxRefundOrderNo(parseWechatTradeBillData(record.get(16)));
        tx.setMchRefundOrderNo(parseWechatTradeBillData(record.get(17)));
        tx.setRefundAmount(new BigDecimal(parseWechatTradeBillData(record.get(18))));
        tx.setEnvelopeRefundAmount(new BigDecimal(parseWechatTradeBillData(record.get(19))));
        tx.setRefundType(parseWechatTradeBillData(record.get(20)));
        tx.setRefundStatus(parseWechatTradeBillData(record.get(21)));
        tx.setName(parseWechatTradeBillData(record.get(22)));
        tx.setPacket(parseWechatTradeBillData(record.get(23)));
        tx.setPoundage(parseWechatTradeBillData(record.get(24)));
        tx.setRate(parseWechatTradeBillData(record.get(25)));
        tx.setOrderAmount(new BigDecimal(parseWechatTradeBillData(record.get(26))));
        return tx;
    }

    private static WechatTransaction getWechatSuccessTransaction(CSVRecord record, SimpleDateFormat sdf) throws ParseException {
        WechatTransaction tx = new WechatTransaction();

        tx.setDate(sdf.parse(parseWechatTradeBillData(record.get(0))));
        tx.setAppId(parseWechatTradeBillData(record.get(1)));
        tx.setMchId(parseWechatTradeBillData(record.get(2)));
        tx.setSubMchId(parseWechatTradeBillData(record.get(3)));
        tx.setDeviceInfo(parseWechatTradeBillData(record.get(4)));
        tx.setWechatOrderNo(parseWechatTradeBillData(record.get(5)));
        tx.setMchOrderNo(parseWechatTradeBillData(record.get(6)));
        tx.setUserId(parseWechatTradeBillData(record.get(7)));
        tx.setType(parseWechatTradeBillData(record.get(8)));
        tx.setStatus(parseWechatTradeBillData(record.get(9)));
        tx.setBank(parseWechatTradeBillData(record.get(10)));
        tx.setCurrency(parseWechatTradeBillData(record.get(11)));
        tx.setAmount(new BigDecimal(parseWechatTradeBillData(record.get(12))));
        tx.setEnvelopeAmount(new BigDecimal(parseWechatTradeBillData(record.get(13))));
        tx.setName(parseWechatTradeBillData(record.get(14)));
        tx.setPacket(parseWechatTradeBillData(record.get(15)));
        tx.setPoundage(parseWechatTradeBillData(record.get(16)));
        tx.setRate(parseWechatTradeBillData(record.get(17)));
        tx.setOrderAmount(new BigDecimal(parseWechatTradeBillData(record.get(18))));
        return tx;
    }


    private static String parseWechatTradeBillData(String data) {
        //明细数据和汇总数据每个字段前会增加1个（`）字符（用于避免获取的内容被excel展示为科学计数法的格式、丢失数据细节）。
        return data.substring(data.indexOf("`") + 1);
    }

}
