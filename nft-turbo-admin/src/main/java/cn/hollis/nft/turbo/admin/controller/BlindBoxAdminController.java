package cn.hollis.nft.turbo.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.admin.infrastructure.exception.AdminException;
import cn.hollis.nft.turbo.admin.param.AdminBlindBoxCollectionCreateParam;
import cn.hollis.nft.turbo.admin.param.AdminBlindBoxCreateParam;
import cn.hollis.nft.turbo.api.box.constant.BlindAllotBoxRule;
import cn.hollis.nft.turbo.api.box.model.BlindBoxVO;
import cn.hollis.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.hollis.nft.turbo.api.box.request.BlindBoxItemCreateRequest;
import cn.hollis.nft.turbo.api.box.request.BlindBoxPageQueryRequest;
import cn.hollis.nft.turbo.api.box.response.BlindBoxCreateResponse;
import cn.hollis.nft.turbo.api.box.service.BlindBoxManageFacadeService;
import cn.hollis.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.hollis.nft.turbo.api.collection.constant.CollectionRarity;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.file.FileService;
import cn.hollis.nft.turbo.web.util.MultiResultConvertor;
import cn.hollis.nft.turbo.web.vo.MultiResult;
import cn.hollis.nft.turbo.web.vo.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.hollis.nft.turbo.admin.infrastructure.exception.AdminErrorCode.ADMIN_UPLOAD_PICTURE_FAIL;
import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.COMMON_TIME_PATTERN;


//盲盒后台管理
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin/box")
@CrossOrigin(origins = "*")
public class BlindBoxAdminController {

    @DubboReference(version = "1.0.0")
    private BlindBoxManageFacadeService blindBoxManageFacadeService;

    @DubboReference(version = "1.0.0")
    private BlindBoxReadFacadeService blindBoxReadFacadeService;

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadBlindBox")
    public Result<String> uploadBlindBox(@RequestParam("file_data") MultipartFile file) throws Exception {
        if (null == file) {
            throw new AdminException(ADMIN_UPLOAD_PICTURE_FAIL);
        }
        String userId = (String) StpUtil.getLoginId();
        //盲盒封面上传
        String prefix = "https://nfturbo-file.oss-cn-hangzhou.aliyuncs.com/";
        String filename = file.getOriginalFilename();
        InputStream fileStream = file.getInputStream();
        String path = "box/" + userId + "/" + filename;
        var res = fileService.upload(path, fileStream);
        if (!res) {
            throw new AdminException(ADMIN_UPLOAD_PICTURE_FAIL);
        }
        return Result.success(prefix + path);

    }

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

    @PostMapping("/createBlindBox")
    public Result<Long> createBlindBox(@Valid @RequestBody AdminBlindBoxCreateParam param) throws Exception {
        String userId = (String) StpUtil.getLoginId();

        if (!isQuantityVerified(param)) {
            return Result.error("BLIND_BOX_BIND_TOTAL_ERROR", "盲盒绑定数目错误");
        }

        BlindBoxCreateRequest request = new BlindBoxCreateRequest();
        BeanUtils.copyProperties(param, request);
        request.setIdentifier(UUID.randomUUID().toString());
        request.setCreatorId(userId);
        request.setCreateTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_PATTERN);
        request.setSaleTime(sdf.parse(param.getSaleTime()));
        if (param.isCanBook()) {
            request.setBookStartTime(sdf.parse(param.getBookStartTime()));
            request.setBookEndTime(sdf.parse(param.getBookEndTime()));
        }
        request.setCanBook(param.isCanBook());
        List<BlindBoxItemCreateRequest> blindBoxItemCreateRequests = new ArrayList<>();
        // 使用 Stream API 进行转换
        param.getCollectionBoxParams().stream()
                .map(collectionCreateParam -> {
                    BlindBoxItemCreateRequest blindBoxItemCreateRequest = new BlindBoxItemCreateRequest();
                    BeanUtils.copyProperties(collectionCreateParam, blindBoxItemCreateRequest);
                    blindBoxItemCreateRequest.setRarity(CollectionRarity.valueOf(collectionCreateParam.getRarity()));
                    return blindBoxItemCreateRequest;
                })
                .forEach(blindBoxItemCreateRequests::add);

        request.setBlindBoxItemCreateRequests(blindBoxItemCreateRequests);
        request.setAllocateRule(BlindAllotBoxRule.RANDOM.name());
        BlindBoxCreateResponse response = blindBoxManageFacadeService.create(request);
        if (response.getSuccess()) {
            return Result.success(response.getBlindBoxId());
        } else {
            return Result.error(response.getResponseCode(), response.getResponseMessage());
        }
    }

    private boolean isQuantityVerified(AdminBlindBoxCreateParam param) {
        Long total = param.getQuantity();

        Long totalQuantity = param.getCollectionBoxParams().stream()
                .mapToLong(AdminBlindBoxCollectionCreateParam::getQuantity)
                .sum();

        return Objects.equals(total, totalQuantity);
    }

    //盲盒列表
    @GetMapping("/blindBoxList")
    public MultiResult<BlindBoxVO> blindBoxList(@NotBlank String state, String keyWord, int pageSize, int currentPage) {
        BlindBoxPageQueryRequest blindBoxPageQueryRequest = new BlindBoxPageQueryRequest();
        blindBoxPageQueryRequest.setState(state);
        blindBoxPageQueryRequest.setKeyword(keyWord);
        blindBoxPageQueryRequest.setCurrentPage(currentPage);
        blindBoxPageQueryRequest.setPageSize(pageSize);
        PageResponse<BlindBoxVO> pageResponse = blindBoxReadFacadeService.pageQueryBlindBox(blindBoxPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

}
