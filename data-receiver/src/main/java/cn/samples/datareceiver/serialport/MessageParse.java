package cn.samples.datareceiver.serialport;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 报文解析接口的实现类，该类能够解析报文并将报文解析的内容提交到数据库
 */
@Service
public class MessageParse implements MessageParseAble {

    /**
     * 报文解析接口的抽象方法实现
     *
     * @param message 接收到的报文
     * @throws SQLException
     */
    @Override
    public void messageParse(String message) throws SQLException {
        System.out.println(message);
//		if (message.startsWith("68") && message.endsWith("16")) {
//			String machineId = message.substring(2, 4);
//			String testType = message.substring(4, 6);
//			System.out.println("设备号："+machineId+" 状态："+testType);
//		} else {
//			throw new RuntimeException("报文格式错误，非法的头部和尾部");
//		}
    }
}
