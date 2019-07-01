package cn.samples.datareceiver.serialport;

import cn.samples.datareceiver.config.RxtxProperties;
import gnu.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * 根据串口上下文设置串口配置，并提供启动和关闭的方法
 */
@Component
public class SerialPortRxtx {

    @Autowired
    RxtxProperties rxtxProperties;

    /**
     * 标识端口是否已被打开
     */
    private boolean isOpen;
    /**
     * 属性描述
     */
    private CommPortIdentifier portId;
    /**
     * 属性描述
     */
    private SerialPort serialPort;
    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * 输出流
     */
    private OutputStream outputStream;

    /**
     * 开启端口 无返回 无参数
     *
     * @author linchunsen
     */
    public void open() throws NoSuchPortException, PortInUseException, IOException {
        portId = CommPortIdentifier.getPortIdentifier(rxtxProperties.getPort());
        serialPort = (SerialPort) portId.open(rxtxProperties.getAppName(), rxtxProperties.getTimeout());
        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();
        isOpen = true;
    }

    /**
     * 关闭端口 无返回 无参数
     *
     * @author linchunsen
     */
    public void close() {
        if (isOpen) {
            try {
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                inputStream.close();
                outputStream.close();
                serialPort.close();
                isOpen = false;
            } catch (IOException ex) {
                // "关闭串口失败";
            }
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public CommPortIdentifier getPortId() {
        return portId;
    }

    public void setPortId(CommPortIdentifier portId) {
        this.portId = portId;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 为串口设置事件监听器
     * 无返回
     *
     * @param listener 事件监听器
     * @throws TooManyListenersException
     * @throws UnsupportedCommOperationException
     * @author linchunsen
     */
    public void addEventListener(SerialPortEventListener listener) throws TooManyListenersException, UnsupportedCommOperationException {
        if (isOpen) {
            this.serialPort.removeEventListener();
            this.serialPort.addEventListener(listener);
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(
                    rxtxProperties.getRate(),
                    rxtxProperties.getDataBits(),
                    rxtxProperties.getStopBits(),
                    rxtxProperties.getParity());
        } else {
            throw new RuntimeException("串口未打开，请检查是否执行了open()方法");
        }
    }

}
