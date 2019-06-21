package cn.samples.datareceiver.xml;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Validate;

import java.io.Serializable;

@Root(name = "DEVICES")
@Setter
@Getter
@ToString
@JsonAutoDetect(JsonMethod.FIELD)
public class DEVICES implements Serializable {
    private static final long serialVersionUID = 1L;

    // 设备模块ID，前端采集软件（默认ID为“00000000”）
    @Element(name = "DEVICE_ID")
    private String deviceId;

    // 设备名称
    @Element(name = "DEVICE_NAME", required = false)
    private String deviceName;

    // 0：禁用 1：正常 2：故障 3：启用
    @Element(name = "STATUS_VALUE")
    private Integer statusValue;

    // 故障代码
    @Element(required = false, name = "ERR_CODE")
    private String errCode;

    /**
     * 日期字段json格式化操作
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Long occurtime = System.currentTimeMillis();

    // CPU占用大小，百分比（最大100%）
    @Element(required = false, name = "SYS_CPU")
    private Byte sysCpu;

    // 内存大小
    @Element(required = false, name = "MEM_ALL")
    private Long memAll;

    // 内存占用情况
    @Element(required = false, name = "MEM_DATA")
    private long memData;

    // 磁盘大小
    @Element(required = false, name = "DISK_SIZE")
    private Long diskSize;

    // 磁盘占用情况
    @Element(required = false, name = "DISK_DATA")
    private Long diskData;

    // 关键设备标识（0：非关键设备，1关键设备）
    @Element(required = false, name = "IS_KEY_DEV")
    private Integer isKeyDev;

    @Validate
    private void validate() throws Exception {
        if (null != errCode && errCode.length() > 20) {
            throw new IllegalArgumentException(String.format("x28 ERR_CODE [%s] can not longer than 20", errCode));
        }

        if (null != deviceId && deviceId.length() > 8) {
            throw new IllegalArgumentException(String.format("x28 DEVICE_ID [%s] can not longer than 8", deviceId));
        }
        if (null != deviceName && deviceName.length() > 50) {
            throw new IllegalArgumentException(
                    String.format("x28 DEVICE_NAME [%s] can not longer than 50", deviceName));
        }
    }

}
