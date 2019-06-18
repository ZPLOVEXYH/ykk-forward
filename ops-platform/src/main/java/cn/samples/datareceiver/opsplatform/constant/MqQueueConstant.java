package cn.samples.datareceiver.opsplatform.constant;

/**
 * MQ 消息队列
 */
public interface MqQueueConstant {

    /**
     * 数据接收 rabbit队列名称
     */
    String DATA_RECEIVER_QUEUE = "data_queue";

    /**
     * 交换机名称
     */
    String EXCHANGE_ROUTE_NAME = "exchange_data_receiver";

    /**
     * 交换机路由名称
     */
    String ROUTING_NAME = "routing_data_receiver";

}
