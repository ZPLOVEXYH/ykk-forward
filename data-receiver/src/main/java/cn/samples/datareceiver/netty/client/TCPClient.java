package cn.samples.datareceiver.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;

/**
 * netty socket netty client
 */
@Slf4j
@Component
public class TCPClient {

    @Autowired
    Bootstrap clientBootstrap;

    @Autowired
    InetSocketAddress clientSocketAddress;

    /**
     * netty socket客户端
     *
     * @throws InterruptedException
     */
    public void connect() throws InterruptedException {
        Channel cl = clientBootstrap
                .connect(clientSocketAddress.getAddress(), clientSocketAddress.getPort())
                .sync()
                .channel();
        String message = "hello client!!!";
        cl.writeAndFlush(message);
        log.info("客户端发送数据：{}, {}", message, LocalDateTime.now());
    }
}
