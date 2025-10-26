package cn.hollis.nft.turbo.api.goods.constant;


public enum GoodsEvent {

    //上链事件
    CHAIN,

    //销毁事件
    DESTROY,

    //出售事件
    SALE,
    TRY_SALE,
    CONFIRM_SALE,
    CANCEL_SALE,
    BLIND_BOX_SALE,


    //空投
    AIR_DROP,

    //转移事件
    TRANSFER,

    //下架
    REMOVE,

    //修改藏品库存
    MODIFY_INVENTORY,

    //修改藏品价格
    MODIFY_PRICE,

    //绑定盲盒事件
    BIND,

    //冻结库存
    FREEZE_INVENTORY,

    //解冻库存
    UNFREEZE_INVENTORY,

    // 解冻库存并出售
    UNFREEZE_AND_SALE;
}
