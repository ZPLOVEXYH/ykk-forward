package cn.samples.datareceiver.serialport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * 报文解析接口的实现类，该类能够解析报文并将报文解析的内容提交到数据库
 */
@Slf4j
@Service
public class MessageParse implements MessageParseAble {

    /**
     * 报文解析接口的抽象方法实现
     *
     * @param message 接收到的报文
     * @throws SQLException
     */
    @Override
    public void messageParse(String message) throws UnsupportedEncodingException {
        System.out.println(message);
        log.info("监听得到的消息内容提供为：{}", message);
        log.info("字符串为：{}", fromHexString(message));
    }

    public static String fromHexString(String hexString) throws UnsupportedEncodingException {
        // 用于接收转换结果
        String result = "";
        // 16进制字符
        String hexDigital = "0123456789ABCDEF";
        // 将16进制字符串转换成char数组
        char[] hexs = hexString.toCharArray();
        // 能被16整除，肯定可以被2整除
        byte[] bytes = new byte[hexString.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = hexDigital.indexOf(hexs[2 * i]) * 16 + hexDigital.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        result = new String(bytes, "UTF-8");

        return result;
    }

}
