package cn.hollis.nft.turbo.order.validator;

import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.OrderException;


//订单校验
public abstract class BaseOrderCreateValidator implements OrderCreateValidator {

    protected OrderCreateValidator nextValidator;

    @Override
    public void setNext(OrderCreateValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    @Override
    public OrderCreateValidator getNext() {
        return nextValidator;
    }

    //校验
    @Override
    public void validate(OrderCreateRequest request) throws OrderException {
        doValidate(request);

        if (nextValidator != null) {
            nextValidator.validate(request);
        }
    }

    protected abstract void doValidate(OrderCreateRequest request) throws OrderException;
}
