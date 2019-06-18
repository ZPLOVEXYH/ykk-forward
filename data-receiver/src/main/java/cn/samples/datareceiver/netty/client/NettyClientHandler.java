package cn.samples.datareceiver.netty.client;

import cn.samples.datareceiver.model.DataPackage;
import cn.samples.datareceiver.simulator.QueueProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class NettyClientHandler extends SimpleChannelInboundHandler<DataPackage> {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    QueueProcessor queueprocessor;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataPackage msg) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();

        queueprocessor.enqueue(new QueueProcessor.MsgData(msg, clientIP, insocket.getPort()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        ctx.fireChannelInactive();
    }
}
