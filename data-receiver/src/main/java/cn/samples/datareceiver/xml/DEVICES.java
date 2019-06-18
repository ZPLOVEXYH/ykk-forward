package cn.samples.datareceiver.xml;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Validate;

import java.io.Serializable;
import java.util.Date;

@Root(name = "DEVICES")
@JsonAutoDetect(JsonMethod.FIELD)
public class DEVICES implements Serializable {
    private static final long serialVersionUID = 1L;

    // 设备模块ID，前端采集软件（默认ID为“00000000”）
    @Element
    @JsonProperty("DEVICE_ID")
    private String DEVICE_ID;

    // 设备名称
    @Element(required = false)
    @JsonProperty("DEVICE_NAME")
    private String DEVICE_NAME;

    // 0：禁用 1：正常 2：故障 3：启用
    @Element
    @JsonProperty("STATUS_VALUE")
    private Integer STATUS_VALUE;

    // 故障代码
    @Element(required = false)
    @JsonProperty("ERR_CODE")
    private String ERR_CODE;

    @JsonProperty("OCCURTIME")
    private Date OCCURTIME = new Date();

    // CPU占用大小，百分比（最大100%）
    @Element(required = false)
    @JsonProperty("SYS_CPU")
    private Byte SYS_CPU;

    // 内存大小
    @Element(required = false)
    @JsonProperty("MEM_ALL")
    private Long MEM_ALL;

    // 内存占用情况
    @Element(required = false)
    @JsonProperty("MEM_DATA")
    private long MEM_DATA;

    // 磁盘大小
    @Element(required = false)
    @JsonProperty("DISK_SIZE")
    private Long DISK_SIZE;

    // 磁盘占用情况
    @Element(required = false)
    @JsonProperty("DISK_DATA")
    private Long DISK_DATA;

    @Validate
    private void validate() throws Exception {
        if (null != ERR_CODE && ERR_CODE.length() > 20) {
            throw new IllegalArgumentException(String.format("x28 ERR_CODE [%s] can not longer than 20", ERR_CODE));
        }

        if (null != DEVICE_ID && DEVICE_ID.length() > 8) {
            throw new IllegalArgumentException(String.format("x28 DEVICE_ID [%s] can not longer than 8", DEVICE_ID));
        }
        if (null != DEVICE_NAME && DEVICE_NAME.length() > 50) {
            throw new IllegalArgumentException(
                    String.format("x28 DEVICE_NAME [%s] can not longer than 50", DEVICE_NAME));
        }
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String dEVICE_ID) {
        DEVICE_ID = dEVICE_ID;
    }

    public String getDEVICE_NAME() {
        return DEVICE_NAME;
    }

    public void setDEVICE_NAME(String dEVICE_NAME) {
        DEVICE_NAME = dEVICE_NAME;
    }

    public Integer getSTATUS_VALUE() {
        return STATUS_VALUE;
    }

    public void setSTATUS_VALUE(Integer sTATUS_VALUE) {
        STATUS_VALUE = sTATUS_VALUE;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }

    public void setERR_CODE(String eRR_CODE) {
        ERR_CODE = eRR_CODE;
    }

    public Date getOCCURTIME() {
        return OCCURTIME;
    }

    public void setOCCURTIME(Date oCCURTIME) {
        OCCURTIME = oCCURTIME;
    }

    public Byte getSYS_CPU() {
        return SYS_CPU;
    }

    public void setSYS_CPU(Byte sYS_CPU) {
        SYS_CPU = sYS_CPU;
    }

    public Long getMEM_ALL() {
        return MEM_ALL;
    }

    public void setMEM_ALL(Long mEM_ALL) {
        MEM_ALL = mEM_ALL;
    }

    public Long getMEM_DATA() {
        return MEM_DATA;
    }

    public void setMEM_DATA(Long mEM_DATA) {
        MEM_DATA = mEM_DATA;
    }

    public Long getDISK_SIZE() {
        return DISK_SIZE;
    }

    public void setDISK_SIZE(Long dISK_SIZE) {
        DISK_SIZE = dISK_SIZE;
    }

    public Long getDISK_DATA() {
        return DISK_DATA;
    }

    public void setDISK_DATA(Long dISK_DATA) {
        DISK_DATA = dISK_DATA;
    }

    @Override
    public String toString() {
        return "DEVICE [DEVICE_ID=" + DEVICE_ID + ", DEVICE_NAME=" + DEVICE_NAME + ", STATUS_VALUE=" + STATUS_VALUE
                + ", ERR_CODE=" + ERR_CODE + ", OCCURTIME=" + OCCURTIME + ", SYS_CPU=" + SYS_CPU + ", MEM_ALL="
                + MEM_ALL + ", MEM_DATA=" + MEM_DATA + ", DISK_SIZE=" + DISK_SIZE + ", DISK_DATA=" + DISK_DATA + "]";
    }

}
