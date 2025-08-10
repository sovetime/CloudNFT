package cn.hollis.nft.turbo.box.facade;

import cn.hollis.nft.turbo.api.box.model.BlindBoxItemVO;
import cn.hollis.nft.turbo.api.box.model.BlindBoxVO;
import cn.hollis.nft.turbo.api.box.model.HeldBlindBoxVO;
import cn.hollis.nft.turbo.api.box.request.BlindBoxItemPageQueryRequest;
import cn.hollis.nft.turbo.api.box.request.BlindBoxPageQueryRequest;
import cn.hollis.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBox;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import cn.hollis.nft.turbo.box.domain.entity.convertor.BlindBoxConvertor;
import cn.hollis.nft.turbo.box.domain.entity.convertor.BlindBoxItemConvertor;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxItemService;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


//盲盒服务
@DubboService(version = "1.0.0")
public class BlindBoxReadFacadeServiceImpl implements BlindBoxReadFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(BlindBoxReadFacadeServiceImpl.class);

    @Autowired
    private BlindBoxService blindBoxService;
    @Autowired
    private BlindBoxItemService blindBoxItemService;
    @Resource
    private InventoryFacadeService inventoryFacadeService;


    @Override
    public SingleResponse<BlindBoxVO> queryById(Long blindBoxId) {
        BlindBox blindBox = blindBoxService.queryById(blindBoxId);

        InventoryRequest request = new InventoryRequest();
        request.setGoodsId(blindBoxId.toString());
        request.setGoodsType(GoodsType.BLIND_BOX);
        SingleResponse<Integer> response = inventoryFacadeService.queryInventory(request);

        //没查到的情况下，默认用数据库里面的库存做兜底
        Integer inventory = blindBox.getSaleableInventory().intValue();
        if (response.getSuccess()) {
            inventory = response.getData();
        }

        BlindBoxVO blindBoxVO = BlindBoxConvertor.INSTANCE.mapToVo(blindBox);
        blindBoxVO.setInventory(inventory.longValue());
        blindBoxVO.setState(blindBox.getState(), blindBox.getSaleTime(), inventory.longValue());
        return SingleResponse.of(blindBoxVO);
    }

    @Override
    public SingleResponse<BlindBoxItemVO> queryBlindBoxItemById(Long blindBoxItemId) {
        BlindBoxItem blindBoxItem = blindBoxItemService.queryById(blindBoxItemId);
        return SingleResponse.of(BlindBoxItemConvertor.INSTANCE.mapToVo(blindBoxItem));
    }

    @Override
    public PageResponse<BlindBoxVO> pageQueryBlindBox(BlindBoxPageQueryRequest request) {
        PageResponse<BlindBox> blindBoxPage = blindBoxService.pageQueryByState(request.getKeyword(), request.getState(), request.getCurrentPage(), request.getPageSize());
        return PageResponse.of(BlindBoxConvertor.INSTANCE.mapToVo(blindBoxPage.getDatas()), blindBoxPage.getTotal(), blindBoxPage.getPageSize(), request.getCurrentPage());
    }

    @Override
    public PageResponse<HeldBlindBoxVO> pageQueryBlindBoxItem(BlindBoxItemPageQueryRequest request) {
        PageResponse<BlindBoxItem> blindBoxItemPage = blindBoxItemService.pageQueryBlindBoxItem(request);
        return PageResponse.of(BlindBoxItemConvertor.INSTANCE.mapToHeldVo(blindBoxItemPage.getDatas()), blindBoxItemPage.getTotal(), blindBoxItemPage.getPageSize(), request.getCurrentPage());
    }
}
