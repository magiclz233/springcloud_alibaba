package com.magic.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author magic_lz
 * @version 1.0
 * @classname TransactionListenerImpl
 * @date 2021/4/28 : 15:59
 */
public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
    
    /**
     * executeLocalTransaction 执行本地事务，返回三个状态之一。
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(message.getTransactionId(), status);
        return LocalTransactionState.UNKNOW;
    }
    
    /**
     * checkLocalTransaction 检查本地事务状态，回应消息队列的检查请求，返回三个状态之一。
     *
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        Integer status = localTrans.get(messageExt.getTransactionId());
        if(status != null){
            switch (status){
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
            }
        }
        
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
