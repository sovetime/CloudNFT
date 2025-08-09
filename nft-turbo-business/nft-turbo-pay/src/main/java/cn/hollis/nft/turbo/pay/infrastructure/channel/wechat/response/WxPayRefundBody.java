package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author Hollis
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WxPayRefundBody {

    private Amount amount;
    private String channel;
    @JSONField(name = "create_time")
    private Date createTime;
    @JSONField(name = "funds_account")
    private String fundsAccount;
    @JSONField(name = "out_refund_no")
    private String outRefundNo;
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    @JSONField(name = "promotion_detail")
    private List<String> promotionDetail;
    @JSONField(name = "refund_id")
    private String refundId;
    private String status;
    @JSONField(name = "transaction_id")
    private String transactionId;
    @JSONField(name = "user_received_account")
    private String userReceivedAccount;

    class Amount {
        private String currency;
        @JSONField(name = "discount_refund")
        private int discountRefund;
        private List<String> from;
        @JSONField(name = "payer_refund")
        private int payerRefund;
        @JSONField(name = "payer_total")
        private int payerTotal;
        private int refund;
        @JSONField(name = "refund_fee")
        private int refundFee;
        @JSONField(name = "settlement_refund")
        private int settlementRefund;
        @JSONField(name = "settlement_total")
        private int settlementTotal;
        private int total;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getDiscountRefund() {
            return discountRefund;
        }

        public void setDiscountRefund(int discountRefund) {
            this.discountRefund = discountRefund;
        }

        public List<String> getFrom() {
            return from;
        }

        public void setFrom(List<String> from) {
            this.from = from;
        }

        public int getPayerRefund() {
            return payerRefund;
        }

        public void setPayerRefund(int payerRefund) {
            this.payerRefund = payerRefund;
        }

        public int getPayerTotal() {
            return payerTotal;
        }

        public void setPayerTotal(int payerTotal) {
            this.payerTotal = payerTotal;
        }

        public int getRefund() {
            return refund;
        }

        public void setRefund(int refund) {
            this.refund = refund;
        }

        public int getRefundFee() {
            return refundFee;
        }

        public void setRefundFee(int refundFee) {
            this.refundFee = refundFee;
        }

        public int getSettlementRefund() {
            return settlementRefund;
        }

        public void setSettlementRefund(int settlementRefund) {
            this.settlementRefund = settlementRefund;
        }

        public int getSettlementTotal() {
            return settlementTotal;
        }

        public void setSettlementTotal(int settlementTotal) {
            this.settlementTotal = settlementTotal;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}


