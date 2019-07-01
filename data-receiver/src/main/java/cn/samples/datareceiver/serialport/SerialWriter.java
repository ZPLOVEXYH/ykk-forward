package cn.samples.datareceiver.serialport;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * 串口通讯的常规读取器
 */
@Component
public class SerialWriter {

    private OutputStream outputStream;

    @Autowired
    SerialPortRxtx serialPortRxtx;

    // 标识串口是否已经初始化
    private boolean isInited = false;

    /**
     * 初始化写串口器
     *
     * @return 初始化后的写串口器对象
     * @throws UnsupportedCommOperationException
     * @throws TooManyListenersException
     * @throws IOException
     * @throws PortInUseException
     * @throws NoSuchPortException
     */
    public SerialWriter init() throws NoSuchPortException, PortInUseException, IOException {
        serialPortRxtx.open();
        this.outputStream = serialPortRxtx.getOutputStream();
        this.isInited = true;
        return this;
    }

    /**
     * 在串口初始化后，向串口中写入字符串
     *
     * @param message 需要写入的字符串
     * @throws IOException
     */
    public void write(String message) throws IOException {
        if (isInited) {
            int length = message.length();
            byte[] bytes = new byte[length / 2];
            for (int i = 0; i < length; i++) {
                bytes[i / 2] = (byte) (((Character.digit(message.charAt(i), 16) << 4)
                        + Character.digit(message.charAt(++i), 16)));
            }
            outputStream.write(bytes);
        } else {
            throw new RuntimeException("写串口器尚未初始化，请执行init()方法后重试");
        }
    }
}
