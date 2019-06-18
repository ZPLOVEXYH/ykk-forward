//package cn.samples.datareceiver.opsplatform.config;
//
//import cn.samples.datareceiver.opsplatform.constant.MqQueueConstant;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * rabbit初始化配置
// */
////@Configuration
//public class RabbitConfig {
//
//    /**
//     * 初始化数据接收队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue queue() {
//        return new Queue(MqQueueConstant.DATA_RECEIVER_QUEUE);
//    }
//
//    /**
//     * 声明一个direct类型的交换机
//     * @return
//     */
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(MqQueueConstant.EXCHANGE_ROUTE_NAME);
//    }
//
//    /**
//     * 队列绑定交换机
//     * @param queue
//     * @param directExchange
//     * @return
//     */
//    @Bean
//    public Binding dataBindingExchange(Queue queue, DirectExchange directExchange) {
//        return BindingBuilder.bind(queue).to(directExchange).with(MqQueueConstant.ROUTING_NAME);
//    }
//
//}
