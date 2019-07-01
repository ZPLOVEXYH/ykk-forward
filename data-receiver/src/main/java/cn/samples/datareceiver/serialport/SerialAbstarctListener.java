package cn.samples.datareceiver.serialport;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

/**
 * 串口事件监听的抽象实现，对串口监听监听器进行了进一步的封装
 */
@Component
public abstract class SerialAbstarctListener extends Observable implements SerialPortEventListener {

    /**
     * 串口实例
     */
    private SerialPortRxtx serialPortRxtx;

    /**
     * 读写延迟
     */
    private long delayReadTime;

    public SerialAbstarctListener(SerialPortRxtx serialPortRxtx) {
        super();
        this.serialPortRxtx = serialPortRxtx;
        this.delayReadTime = serialPortRxtx.rxtxProperties.getDelayRead();
    }

    /**
     * {@link SerialPortEventListener}接口的实现类
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (serialPortRxtx.isOpen()) {
            try {
                Thread.sleep(delayReadTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (event.getEventType()) {
                case SerialPortEvent.BI: // 10
                case SerialPortEvent.OE: // 7
                case SerialPortEvent.FE: // 9
                case SerialPortEvent.PE: // 8
                case SerialPortEvent.CD: // 6
                case SerialPortEvent.CTS: // 3
                case SerialPortEvent.DSR: // 4
                case SerialPortEvent.RI: // 5
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2
                    break;
                case SerialPortEvent.DATA_AVAILABLE: // 1
                    InputStream inputStream = serialPortRxtx.getInputStream();
                    OutputStream outputStream = serialPortRxtx.getOutputStream();
                    readerAndWriter(inputStream, outputStream);
                    break;
            }
        } else {
            throw new RuntimeException("串口未开启，请查看是否执行了SerialPortRxtx类的open()方法");
        }
    }

    /**
     * 每次在接收合法数据时执行的读写操作 无返回
     *
     * @param inputStream  串口输入流
     * @param outputStream 串口输出流
     */
    public abstract void readerAndWriter(InputStream inputStream, OutputStream outputStream);
}
