package cn.samples.datareceiver.constant;

/**
 * MQ 消息队列
 */
public interface MqQueueConstant {

    /**
     * 数据发送 rabbit队列名称
     */
    String DATA_SENDER_QUEUE = "data_queue";

    /**
     * 交换机名称
     */
    String EXCHANGE_ROUTE_NAME = "exchange_data_sender";

    /**
     * 交换机路由名称
     */
    String ROUTING_NAME = "routing_data_sender";

}
