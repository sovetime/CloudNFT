package cn.hollis.nft.turbo.order.validator;

import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.OrderException;


//订单校验
public interface OrderCreateValidator {

    //设置下一个校验器
    public void setNext(OrderCreateValidator nextValidator);

    //返回下一个校验器
    public OrderCreateValidator getNext();

    //订单校验
    public void validate(OrderCreateRequest request) throws OrderException;
}
