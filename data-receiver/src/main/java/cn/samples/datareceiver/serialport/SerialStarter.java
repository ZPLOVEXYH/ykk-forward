package cn.samples.datareceiver.serialport;

import cn.samples.datareceiver.config.RxtxProperties;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * 串口通讯的启动类
 */
@Component
@Slf4j
public class SerialStarter {

    @Autowired
    RxtxProperties rxtxProperties;

    @Autowired
    SerialWriter serialWriter;

    @Autowired
    SerialReader serialReader;

    private List<SerialWriter> serialWriters = null;


    /**
     * 将传入的消息逐次发给各个串口
     *
     * @throws IOException
     * @throws UnsupportedCommOperationException
     * @throws TooManyListenersException
     * @throws PortInUseException
     * @throws NoSuchPortException
     */
    public void wirte(String message) throws IOException, NoSuchPortException, PortInUseException, TooManyListenersException, UnsupportedCommOperationException {
        //如果写串口器列表还未初始化，则初始化
        if (serialWriters == null) {
            serialWriters = new ArrayList<>();
            serialWriters.add(serialWriter.init());
        }

        //将消息逐个写到串口
        for (SerialWriter serialWriter : serialWriters) {
            serialWriter.write(message);
        }

    }

    /**
     * 根据传入的报文解析器，以后台进程的形式启动串口读取器
     *
     * @param messageParser 传入的报文解析器
     */
    public void startReader() {
//        List<String> listenerPortList = rxtxProperties.getListenerPortList();
//
//        for (String port : listenerPortList) {
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        serialReader.startReaderListener();
//                    } catch (NoSuchPortException e) {
//                        e.printStackTrace();
//                    } catch (PortInUseException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TooManyListenersException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedCommOperationException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//
//            log.info("正在监听串口:{} ", port.toUpperCase());
//        }

        new Thread() {
            @Override
            public void run() {
                try {
                    serialReader.startReaderListener();
                } catch (NoSuchPortException e) {
                    e.printStackTrace();
                } catch (PortInUseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        log.info("正在监听串口:{} ", rxtxProperties.getPort().toUpperCase());
    }

}
