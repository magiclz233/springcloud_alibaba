package com.magic.rocketmq.ordermessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 根据订单分组传输数据,每组组内数据保持顺序
 *
 * @author magic_lz
 * @version 1.0
 * @classname Producer
 * @date 2020/8/19 : 14:52
 */
public class Producer {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_order_demo_one");
        producer.setNamesrvAddr(com.magic.rocketmq.quickstart.Producer.NAME_SERVER_ADDR);
        producer.start();

        String[] tags = new String[]{"TagA", "TagC", "TagD"};
        List<OrderStep> orderList = new OrderStep().buildOrders();
        String now = LocalDateTime.now().toString();
        for (int i = 0; i < orderList.size(); i++) {
            String body = now + "Hello RocketMQ" + orderList.get(i);
            Message msg = new Message("TopicTest", tags[i % tags.length], "Key" + i, body.getBytes());
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                /*
                这块是重新定义Producer发送消息传入的队列的规则
                后面的 orderList.get(i).getOrderId() 是你要传入的来制定规则的参数
                此处用了orderId
                在 select 就是 最后的 Object 参数
                 当 producer send msg后
                 根据orderId % List<MessageQueue> 将msg传入到不同的queue中去
                 此处可以根据自己需求定义不同的传入队列规则
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message msg, Object o) {
                    Long id = (Long) o;
                    long index = id % list.size();
                    return list.get((int) index);
                }
            }, orderList.get(i).getOrderId());
            System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s",
                    sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(),
                    body));
        }
        producer.shutdown();
    }


    private static class OrderStep {
        private Long orderId;
        private String desc;

        private List<OrderStep> buildOrders() {
            List<OrderStep> orderList = new ArrayList<OrderStep>();

            OrderStep orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("创建");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111065L);
            orderDemo.setDesc("创建");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("付款");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103117235L);
            orderDemo.setDesc("创建");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111065L);
            orderDemo.setDesc("付款");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103117235L);
            orderDemo.setDesc("付款");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111065L);
            orderDemo.setDesc("完成");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("推送");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103117235L);
            orderDemo.setDesc("完成");
            orderList.add(orderDemo);

            orderDemo = new OrderStep();
            orderDemo.setOrderId(15103111039L);
            orderDemo.setDesc("完成");
            orderList.add(orderDemo);

            return orderList;
        }

        @Override
        public String toString() {
            return "OrderStep{" +
                    "orderId=" + orderId +
                    ", desc='" + desc + '\'' +
                    '}';
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
