package cn.time.nft.turbo.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.time.nft.turbo.admin.infrastructure.exception.AdminException;
import cn.time.nft.turbo.admin.param.AdminCollectionAirDropParam;
import cn.time.nft.turbo.admin.param.AdminCollectionCreateParam;
import cn.time.nft.turbo.admin.param.AdminCollectionModifyParam;
import cn.time.nft.turbo.admin.param.AdminCollectionRemoveParam;
import cn.time.nft.turbo.api.chain.service.ChainFacadeService;
import cn.time.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.time.nft.turbo.api.collection.model.AirDropStreamVO;
import cn.time.nft.turbo.api.collection.model.CollectionVO;
import cn.time.nft.turbo.api.collection.request.*;
import cn.time.nft.turbo.api.collection.response.CollectionAirdropResponse;
import cn.time.nft.turbo.api.collection.response.CollectionChainResponse;
import cn.time.nft.turbo.api.collection.response.CollectionModifyResponse;
import cn.time.nft.turbo.api.collection.response.CollectionRemoveResponse;
import cn.time.nft.turbo.api.collection.service.CollectionManageFacadeService;
import cn.time.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.time.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.time.nft.turbo.api.inventory.request.InventoryRequest;
import cn.time.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.time.nft.turbo.api.user.service.UserFacadeService;
import cn.time.nft.turbo.base.response.PageResponse;
import cn.time.nft.turbo.file.FileService;
import cn.time.nft.turbo.web.util.MultiResultConvertor;
import cn.time.nft.turbo.web.vo.MultiResult;
import cn.time.nft.turbo.web.vo.Result;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static cn.time.nft.turbo.admin.infrastructure.exception.AdminErrorCode.ADMIN_UPLOAD_PICTURE_FAIL;
import static cn.time.nft.turbo.api.common.constant.CommonConstant.COMMON_TIME_PATTERN;


//藏品后台管理
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin/collection")
@CrossOrigin(origins = "*")
public class CollectionAdminController {

    @DubboReference(version = "1.0.0")
    private CollectionManageFacadeService collectionManageFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private CollectionReadFacadeService collectionReadFacadeService;

    @DubboReference(version = "1.0.0")
    private ChainFacadeService chainFacadeService;

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @Autowired
    private FileService fileService;

    @DubboReference(version = "1.0.0")
    private InventoryFacadeService inventoryFacadeService;
    //上传藏品
    @PostMapping("/uploadCollection")
    public Result<String> uploadCollection(@RequestParam("file_data") MultipartFile file) throws Exception {
        if (null == file) {
            throw new AdminException(ADMIN_UPLOAD_PICTURE_FAIL);
        }
        String userId = (String) StpUtil.getLoginId();
        //藏品封面上传
        String prefix = "https://nfturbo-file.oss-cn-hangzhou.aliyuncs.com/";
        String filename = file.getOriginalFilename();
        InputStream fileStream = file.getInputStream();
        String path = "collection/" + userId + "/" + filename;
        var res = fileService.upload(path, fileStream);
        if (!res) {
            throw new AdminException(ADMIN_UPLOAD_PICTURE_FAIL);
        }
        return Result.success(prefix + path);

    }

    //创建藏品
    @PostMapping("/createCollection")
    public Result<Long> createCollection(@Valid @RequestBody AdminCollectionCreateParam param) throws Exception {
        String userId = (String) StpUtil.getLoginId();

        CollectionCreateRequest request = new CollectionCreateRequest();
        request.setIdentifier(UUID.randomUUID().toString());
        request.setPrice(param.getPrice());
        request.setQuantity(param.getQuantity());
        request.setName(param.getName());
        request.setDetail(param.getDetail());
        request.setCover(param.getCover());
        request.setCreatorId(userId);
        request.setCreateTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_PATTERN);
        request.setSaleTime(sdf.parse(param.getSaleTime()));
        if (param.getCanBook() == 1) {
            request.setBookStartTime(sdf.parse(param.getBookStartTime()));
            request.setBookEndTime(sdf.parse(param.getBookEndTime()));
        }
        request.setCanBook(param.getCanBook());
        CollectionChainResponse response = collectionManageFacadeService.create(request);
        if (response.getSuccess()) {
            return Result.success(response.getCollectionId());
        } else {
            return Result.error(response.getResponseCode(), response.getResponseMessage());
        }
    }

    //移除藏品
    @PostMapping("/removeCollection")
    public Result<Long> removeCollection(@Valid @RequestBody AdminCollectionRemoveParam param) {
        CollectionRemoveRequest request = new CollectionRemoveRequest();
        request.setIdentifier(UUID.randomUUID().toString());
        request.setCollectionId(param.getCollectionId());
        CollectionRemoveResponse response = collectionManageFacadeService.remove(request);
        if (response.getSuccess()) {
            return Result.success(response.getCollectionId());
        } else {
            return Result.error(response.getResponseCode(), response.getResponseMessage());
        }
    }

    //修改藏品库存
    @PostMapping("/modifyInventory")
    public Result<Long> modifyInventory(@Valid @RequestBody AdminCollectionModifyParam param) {
        CollectionModifyInventoryRequest request = new CollectionModifyInventoryRequest();
        request.setIdentifier(UUID.randomUUID().toString());
        request.setCollectionId(param.getCollectionId());
        request.setQuantity(param.getQuantity());
        CollectionModifyResponse response = collectionManageFacadeService.modifyInventory(request);
        if (response.getSuccess()) {
            return Result.success(response.getCollectionId());
        } else {
            return Result.error(response.getResponseCode(), response.getResponseMessage());
        }

    }

    //修改藏品价格
    @PostMapping("/modifyPrice")
    public Result<Long> modifyPrice(@Valid @RequestBody AdminCollectionModifyParam param) {
        CollectionModifyPriceRequest request = new CollectionModifyPriceRequest();
        request.setIdentifier(UUID.randomUUID().toString());
        request.setCollectionId(param.getCollectionId());
        request.setPrice(param.getPrice());
        CollectionModifyResponse response = collectionManageFacadeService.modifyPrice(request);
        if (response.getSuccess()) {
            return Result.success(response.getCollectionId());
        } else {
            return Result.error(response.getResponseCode(), response.getResponseMessage());
        }
    }

    //藏品列表
    @GetMapping("/collectionList")
    public MultiResult<CollectionVO> collectionList(String state, String keyWord, int pageSize, int currentPage) {
        CollectionPageQueryRequest collectionPageQueryRequest = new CollectionPageQueryRequest();
        collectionPageQueryRequest.setState(state);
        collectionPageQueryRequest.setKeyword(keyWord);
        collectionPageQueryRequest.setCurrentPage(currentPage);
        collectionPageQueryRequest.setPageSize(pageSize);
        //藏品分页查询
        PageResponse<CollectionVO> pageResponse = collectionReadFacadeService.pageQuery(collectionPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    //空投
    @PostMapping("/airDrop")
    public Result<String> airDrop(@Valid @RequestBody AdminCollectionAirDropParam param) {
        CollectionAirDropRequest request = new CollectionAirDropRequest(param.getRecipientUserId(), param.getQuantity(), GoodsSaleBizType.valueOf(param.getBizType()));
        // 防止重复提交的幂等号生成策略：入参的md5+时间戳(精确到分钟），一分钟内同一个参数只能提交一次
        request.setIdentifier(MD5.create().digestHex(JSON.toJSONString(param)) + DateUtils.truncate(new Date(), Calendar.MINUTE).getTime());
        request.setCollectionId(param.getCollectionId());
        CollectionAirdropResponse airdropResponse = collectionManageFacadeService.airDrop(request);

        if (airdropResponse.getSuccess()) {
            //同步写redis，如果失败，不阻塞流程，靠binlog同步保障
            try {
                InventoryRequest inventoryRequest = new InventoryRequest(request);
                inventoryFacadeService.decrease(inventoryRequest);
            } catch (Exception e) {
                log.error("decrease inventory from redis failed", e);
            }

            return Result.success(airdropResponse.getAirDropStreamId().toString());
        } else {
            return Result.error(airdropResponse.getResponseCode(), airdropResponse.getResponseMessage());
        }
    }

    //空投列表
    @GetMapping("/airDropList")
    public MultiResult<AirDropStreamVO> airDropList(String collectionId, String userId, int pageSize, int currentPage) {
        AirDropPageQueryRequest request = new AirDropPageQueryRequest();
        request.setCollectionId(collectionId);
        request.setUserId(userId);
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<AirDropStreamVO> pageResponse = collectionReadFacadeService.pageQueryAirDropList(request);
        return MultiResultConvertor.convert(pageResponse);
    }
}
