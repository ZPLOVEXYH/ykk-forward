//package cn.samples.datareceiver.opsplatform.listener;
//
//import cn.samples.datareceiver.opsplatform.constant.MqQueueConstant;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMqListener {
//
//    @RabbitListener(queues = MqQueueConstant.DATA_RECEIVER_QUEUE)
//    public void receive(String message) {
//        String str = new String(message);
//        System.out.println("收到的rabbitmq的消息内容为：" + str);
//    }
//}
