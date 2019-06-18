package cn.samples.datareceiver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * netty属性值对象
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {
    @NotNull
    private String hostName;

    @NotNull
    private int tcpPort;

//    @NotNull
//    private List<Integer> portList;

    @NotNull
    private int bossCount;

    @NotNull
    private int workerCount;

    @NotNull
    private boolean keepAlive;

    @NotNull
    private int backlog;
}
