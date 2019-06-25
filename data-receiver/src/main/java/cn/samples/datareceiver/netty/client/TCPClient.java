package cn.samples.datareceiver.netty.client;

import io.netty.bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

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
        clientBootstrap
                .connect(clientSocketAddress.getAddress(), clientSocketAddress.getPort())
                .sync()
                .channel();
    }
}
