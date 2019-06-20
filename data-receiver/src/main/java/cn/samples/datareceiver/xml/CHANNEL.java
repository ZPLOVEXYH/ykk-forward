package cn.samples.datareceiver.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "DEVICE_STATUS")
@Getter
@Setter
@ToString
public class CHANNEL implements Serializable {

    @Attribute(name = "AREA_ID")
    protected String areaId;

    // 通道编号（10位）
    @Attribute(name = "CHNL_NO")
    protected String chnlNo;

    // 通道状态：未登陆、正常（登陆）、注销、故障、告警
    protected String chnlState = "正常（登陆）";

    // 最近一次的更新时间
    protected Long lastUpdateTime = System.currentTimeMillis();

    // I表示进卡口，O（字母）表示出卡口
    @Attribute(name = "I_E_TYPE")
    protected String ieType;
}
