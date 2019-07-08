package cn.samples.datareceiver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 读取配置文件信息
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "serial.rxtx")
public class RxtxProperties {

    /**
     * 延时等待端口数据准备的时间
     */
    @NotNull
    private Integer delayRead;

    /**
     * 超时时间
     */
    @NotNull
    private Integer timeout;

    /**
     * 串口名集合
     */
    @NotNull
    private String port;

    /**
     * 数据位
     */
    @NotNull
    private Integer dataBits;

    /**
     * 停止位
     */
    @NotNull
    private Integer stopBits;

    /**
     * 奇偶校验
     */
    @NotNull
    private Integer parity;

    /**
     * 波特率
     */
    @NotNull
    private Integer rate;

    /**
     * 串口的程序名
     */
    @NotNull
    private String appName;
}
