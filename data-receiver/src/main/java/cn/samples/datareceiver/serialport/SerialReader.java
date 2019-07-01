package cn.samples.datareceiver.serialport;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * 串口通讯的常规读取器
 */
@Component
public class SerialReader {

    @Autowired
    SerialStarter serialStarter;

    @Autowired
    SerialPortRxtx serialPortRxtx;

    // 据传入的串口配置上下文和报文解析器来构建报文读取器
    @Autowired
    MessageParseAble messageParseAble;

    /**
     * 启动串口的读监听器 无返回 无参数
     *
     * @throws UnsupportedCommOperationException
     * @throws TooManyListenersException
     * @throws IOException
     * @throws PortInUseException
     * @throws NoSuchPortException
     * @author linchunsen
     */
    public void startReaderListener() throws NoSuchPortException, PortInUseException, IOException, TooManyListenersException, UnsupportedCommOperationException {
        serialPortRxtx.open();
        SerialPortEventListener listener = new SerialAbstarctListener(serialPortRxtx) {
            private byte[] datas = new byte[1024];

            @Override
            public void readerAndWriter(InputStream inputStream, OutputStream outputStream) {
                try {
                    if (inputStream.available() > 0) {
                        System.out.println("test," + new String(datas));
                        int size = inputStream.read(datas);
                        StringBuilder dataBuilder = new StringBuilder();
                        String iString;
                        for (int i = 0; i < size; i++) {
                            //解码并输出数据
                            System.out.print((char) datas[i]);
                            iString = Integer.toHexString(datas[i] & 0xFF);
                            if (iString.length() == 1) {
                                iString = "0" + iString;
                            }
                            dataBuilder.append(iString);
                        }

                        String message = dataBuilder.toString().toUpperCase();
                        messageParseAble.messageParse(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        serialPortRxtx.addEventListener(listener);
    }
}
