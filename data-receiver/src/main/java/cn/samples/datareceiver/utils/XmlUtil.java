package cn.samples.datareceiver.utils;

import org.apache.commons.codec.binary.StringUtils;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * Xml报文序列化/反序列化工具类
 */
public class XmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    private static final Persister persister = new Persister();

    /**
     * 解析xml报文
     * 默认使用严格模式：xml中所有节点必须在对象中有定义
     *
     * @param xml
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String xml, Class<T> clazz) {
        return deserialize(xml, clazz, true);
    }

    /**
     * 解析xml报文
     *
     * @param xml      xml报文内容
     * @param clazz<T> 解析后的目标类型
     * @param strict   是否使用严格模式
     *                 严格模式下，xml中所有节点必须在对象中有定义
     *                 非严格模式下，xml中未定义的节点会被忽略
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String xml, Class<T> clazz, boolean strict) {
        try {
            return persister.read(clazz, xml, strict); // 使用非严格模式，忽略多余的字段
        } catch (Exception e) {
            logger.error("解析{}报文失败，xml={}, e={}", clazz.getSimpleName(), xml, e);
        }

        return null;
    }

    /**
     * 构建Xml报文
     *
     * @param source  待构建xml的对象
     * @param charset 字符编码
     * @return
     */
    public static byte[] serialize(Object source, String charset) {
        // 设置报文长度、报文内容等
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            persister.write(source, bos, charset);
        } catch (Exception e) {
            logger.error("构建{}报文失败，model={}, e={}", source.getClass().getSimpleName(), source, e);
        }
        return bos.toByteArray();
    }

    /**
     * 构建xml报文
     *
     * @param source
     * @param charset
     * @return
     */
    public static String serializeToStr(Object source, String charset) {
        return StringUtils.newString(serialize(source, charset), charset);
    }
}
