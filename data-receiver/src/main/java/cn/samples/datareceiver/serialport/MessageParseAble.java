package cn.samples.datareceiver.serialport;

/**
 * 报文解析接口,只有实现了该接口的类才能被串口读取器所调用以解析报文
 */
public interface MessageParseAble {
    /**
     * 解析执行报文
     *
     * @param message 报文
     * @throws Exception
     */
    void messageParse(String message) throws Exception;
}
