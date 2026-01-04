package cn.time.nft.turbo.tcc.service;

import cn.time.nft.turbo.tcc.entity.*;
import cn.time.nft.turbo.tcc.mapper.TransactionLogMapper;
import cn.time.nft.turbo.tcc.request.TccRequest;
import cn.time.nft.turbo.tcc.response.TransactionCancelResponse;
import cn.time.nft.turbo.tcc.response.TransactionConfirmResponse;
import cn.time.nft.turbo.tcc.response.TransactionTryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


//TCC服务方法
//解决防悬挂、空回滚问题
public class TransactionLogService extends ServiceImpl<TransactionLogMapper, TransactionLog> {

    //try失败
    private final static String TRY_FAILED = "TRY_FAILED";

    //confirm失败
    private final static String CONFIRM_FAILED = "CONFIRM_FAILED";

    //空回滚失败
    private final static String EMPTY_CANCEL_FAILED = "EMPTY_CANCEL_FAILED";

    //取消失败
    private final static String CANCEL_FAILED = "CANCEL_FAILED";

    //TCC事务的try，设置订单状态为try（推进），处理重复try
    public TransactionTryResponse tryTransaction(TccRequest tccRequest) {
        //判断事务日志是否存在
        TransactionLog existTransactionLog = getExistTransLog(tccRequest);
        //如果不存在，则插入一条Try数据
        if (existTransactionLog == null) {
            TransactionLog transactionLog = new TransactionLog(tccRequest, TransActionLogState.TRY);
            if (this.save(transactionLog)) {
                return new TransactionTryResponse(true, TransTrySuccessType.TRY_SUCCESS);
            }
            return new TransactionTryResponse(false, "500", TRY_FAILED);
        }

        //重复try处理
        return new TransactionTryResponse(true, TransTrySuccessType.DUPLICATED_TRY);
    }

    //TCC事务的Confirm，设置订单状态为confirm(推进），处理重复confirm
    public TransactionConfirmResponse confirmTransaction(TccRequest tccRequest) {
        //判断事务日志是否存在
        TransactionLog existTransactionLog = getExistTransLog(tccRequest);
        if (existTransactionLog == null) {
            throw new UnsupportedOperationException("transacton can not confirm");
        }

        //如果是Try状态，则修改为Confirm成功
        if (existTransactionLog.getState() == TransActionLogState.TRY) {
            existTransactionLog.setState(TransActionLogState.CONFIRM);
            if (this.updateById(existTransactionLog)) {
                return new TransactionConfirmResponse(true, TransConfirmSuccessType.CONFIRM_SUCCESS);
            }
            return new TransactionConfirmResponse(false, "500", CONFIRM_FAILED);
        }

        //重复confirm处理
        if (existTransactionLog.getState() == TransActionLogState.CONFIRM) {
            return new TransactionConfirmResponse(true, TransConfirmSuccessType.DUPLICATED_CONFIRM);
        }

        //不支持的状态
        throw new UnsupportedOperationException("transacton can not confirm :" + existTransactionLog.getState());
    }

    //TCC事务的cancel，设置订单状态为cancel（推进）,处理悬挂、重复cancal
    public TransactionCancelResponse cancelTransaction(TccRequest tccRequest) {
        //判断事务日志是否存在
        TransactionLog existTransactionLog = getExistTransLog(tccRequest);
        //数据库没有对应记录（没有try，发生悬挂，出现空回滚问题)，直接记录一条状态为cancel的数据
        if (existTransactionLog == null) {
            TransactionLog transactionLog = new TransactionLog(tccRequest, TransActionLogState.CANCEL, TransCancelSuccessType.EMPTY_CANCEL);
            if (this.save(transactionLog)) {
                return new TransactionCancelResponse(true, TransCancelSuccessType.EMPTY_CANCEL);
            }
            return new TransactionCancelResponse(false, "500", EMPTY_CANCEL_FAILED);
        }

        //如果是Try状态，则修改为cancel状态
        if (existTransactionLog.getState() == TransActionLogState.TRY) {
            existTransactionLog.setState(TransActionLogState.CANCEL);
            existTransactionLog.setCancelType(TransCancelSuccessType.CANCEL_AFTER_TRY_SUCCESS);
            if (this.updateById(existTransactionLog)) {
                return new TransactionCancelResponse(true, TransCancelSuccessType.CANCEL_AFTER_TRY_SUCCESS);
            }
            return new TransactionCancelResponse(false, "500", CANCEL_FAILED);
        }

        //如果是Confirm状态，则修改为cancel状态
        if (existTransactionLog.getState() == TransActionLogState.CONFIRM) {
            existTransactionLog.setState(TransActionLogState.CANCEL);
            existTransactionLog.setCancelType(TransCancelSuccessType.CANCEL_AFTER_CONFIRM_SUCCESS);
            if (this.updateById(existTransactionLog)) {
                return new TransactionCancelResponse(true, TransCancelSuccessType.CANCEL_AFTER_CONFIRM_SUCCESS);
            }
            return new TransactionCancelResponse(false, "500", CANCEL_FAILED);
        }

        //重复cancel处理
        if (existTransactionLog.getState() == TransActionLogState.CANCEL) {
            return new TransactionCancelResponse(true, TransCancelSuccessType.DUPLICATED_CANCEL);
        }

        return new TransactionCancelResponse(false, "500", CANCEL_FAILED);
    }

    //判断事务日志是否存在
    private TransactionLog getExistTransLog(TccRequest request) {
        QueryWrapper<TransactionLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("transaction_id", request.getTransactionId());
        queryWrapper.eq("business_scene", request.getBusinessScene());
        queryWrapper.eq("business_module", request.getBusinessModule());
        return this.getOne(queryWrapper);
    }

}
