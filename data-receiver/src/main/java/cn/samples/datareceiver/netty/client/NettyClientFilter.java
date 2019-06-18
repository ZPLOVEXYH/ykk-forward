package cn.samples.datareceiver.netty.client;

import cn.samples.datareceiver.codec.DataDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {

    @Autowired
    NettyClientHandler nettyClientHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new DataDecoder());
//        channelPipeline.addLast(new StringDecoder());
        channelPipeline.addLast(nettyClientHandler);
    }
}
