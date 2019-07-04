package cn.samples.datareceiver.service.impl;

import cn.samples.datareceiver.serialport.port.SerialPortUtils;
import cn.samples.datareceiver.service.MessageParseAble;
import cn.samples.datareceiver.utils.RedisUtil;
import cn.samples.datareceiver.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 报文解析接口的实现类，该类能够解析报文并将报文解析的内容提交到数据库
 */
@Slf4j
@Service
public class MessageParse implements MessageParseAble {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 报文解析接口的抽象方法实现
     *
     * @param message 接收到的报文
     * @throws SQLException
     */
    @Override
    public void messageParse(String message, int sumLength, int dataByteLength) {
        log.info("获取得到的报文内容字节数为：{}， 总的字节数为：{}", sumLength, dataByteLength);
        if (sumLength == dataByteLength) {
            SerialPortUtils.sumLength = 0;
            log.info("监听得到的消息内容提供为：{}", message);
            // 如果内容不为空，那么将内容保存到redis当中
            if (!StringUtils.isBlank(message)) {
                redisUtil.saveToSetCache("channel_info", message);
            }
        }
    }

}
