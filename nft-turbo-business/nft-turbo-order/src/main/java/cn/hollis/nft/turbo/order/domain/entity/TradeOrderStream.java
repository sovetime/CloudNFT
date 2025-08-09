package cn.hollis.nft.turbo.order.domain.entity;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hollis
 */
@Getter
@Setter
@NoArgsConstructor
public class TradeOrderStream extends BaseEntity {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 买家id
     */
    private String buyerId;

    /**
     * 买家id类型
     */
    private UserType buyerType;

    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * 卖家id类型
     */
    private UserType sellerType;

    /**
     * 订单幂等号
     */
    private String identifier;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 商品数量
     */
    private int itemCount;

    /**
     * 商品单价
     */
    private BigDecimal itemPrice;

    /**
     * 已支付金额
     */
    private BigDecimal paidAmount;

    /**
     * 支付成功时间
     */
    private Date paySucceedTime;

    /**
     * 下单确认时间
     */
    private Date orderConfirmedTime;

    /**
     * 下单确认时间
     */
    private Date orderFinishedTime;

    /**
     * 订单关闭时间
     */
    private Date orderClosedTime;

    /**
     * 商品Id
     */
    private String goodsId;

    /**
     * 商品类型
     */
    private GoodsType goodsType;

    /**
     * 商品图片
     */
    private String goodsPicUrl;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 支付方式
     */
    private PayChannel payChannel;

    /**
     * 支付流水号d
     */
    private String payStreamId;

    /**
     * 订单状态
     */
    private TradeOrderState orderState;

    /**
     * 关单类型
     */
    private String closeType;

    /**
     * 流水类型
     */
    private TradeOrderEvent streamType;

    /**
     * 操作幂等号
     */
    private String streamIdentifier;

    public TradeOrderStream(TradeOrder tradeOrder, TradeOrderEvent streamType, String streamIdentifier) {
        this.orderId = tradeOrder.getOrderId();
        this.buyerId = tradeOrder.getBuyerId();
        this.buyerType = tradeOrder.getBuyerType();
        this.sellerId = tradeOrder.getSellerId();
        this.sellerType = tradeOrder.getSellerType();
        this.identifier = tradeOrder.getIdentifier();
        this.orderAmount = tradeOrder.getOrderAmount();
        this.paidAmount = tradeOrder.getPaidAmount();
        this.paySucceedTime = tradeOrder.getPaySucceedTime();
        this.orderConfirmedTime = tradeOrder.getOrderConfirmedTime();
        this.orderFinishedTime = tradeOrder.getOrderFinishedTime();
        this.orderClosedTime = tradeOrder.getOrderClosedTime();
        this.goodsId = tradeOrder.getGoodsId();
        this.goodsType = tradeOrder.getGoodsType();
        this.goodsName = tradeOrder.getGoodsName();
        this.goodsPicUrl = tradeOrder.getGoodsPicUrl();
        this.payChannel = tradeOrder.getPayChannel();
        this.payStreamId = tradeOrder.getPayStreamId();
        this.orderState = tradeOrder.getOrderState();
        this.closeType = tradeOrder.getCloseType();
        super.setLockVersion(tradeOrder.getLockVersion());
        super.setDeleted(tradeOrder.getDeleted());
        this.itemCount = tradeOrder.getItemCount();
        this.itemPrice = tradeOrder.getItemPrice();
        this.streamType = streamType;
        this.streamIdentifier = streamIdentifier;
    }
}
