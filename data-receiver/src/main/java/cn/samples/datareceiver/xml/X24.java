package cn.samples.datareceiver.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * 0x24 卡口设备状态信息（XMLInfoDeviceStatus）
 * 
 * @author yinheng
 *
 */
@Root(name = "DEVICE_STATUS")
@Setter
@Getter
@ToString
public class X24 /* extends BaseModel */ implements Serializable {
	private static final long serialVersionUID = 1L;

	// 场站编号（10位）
	@Attribute(name = "AREA_ID")
	protected String areaId;

	// 通道编号（10位）
	@Attribute(name = "CHNL_NO")
	protected String chalNo;

	// 通道状态：未登陆、正常（登陆）、注销、故障、告警
	protected String chalState;

	// 最近一次的更新时间
	protected Long lastUpdateTime;

	// I表示进卡口，O（字母）表示出卡口
	@Attribute(name = "I_E_TYPE", required = false)
	protected String ieType;

	// 如果卡口没有此设备，则相应的标签就不存在
	@ElementList(inline = true, required = false, name = "DEVICES")
	private List<DEVICES> devices;



}
