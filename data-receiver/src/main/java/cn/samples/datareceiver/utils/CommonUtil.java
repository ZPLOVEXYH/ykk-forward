package cn.samples.datareceiver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yhllo on 2017/8/3.
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 解析报文
     *
     * @param xml
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T parseXmlModel(String xml, Class<T> clazz) {
        return XmlUtil.deserialize(xml, clazz, false);
    }
}
