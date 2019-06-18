package cn.samples.datareceiver.xml;

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
public class X24 /* extends BaseModel */ implements Serializable {
	private static final long serialVersionUID = 1L;

	@Attribute
	protected String AREA_ID; // 场站编号（10位）
	@Attribute
	protected String CHNL_NO; // 通道编号（10位）
	@Attribute(required = false)
	protected String I_E_TYPE; // I表示进卡口，O（字母）表示出卡口

	@ElementList(inline = true, required = false)
	private List<DEVICES> DEVICES; // 如果卡口没有此设备，则相应的标签就不存在

	public String getAREA_ID() {
		return AREA_ID;
	}

	public void setAREA_ID(String aREA_ID) {
		AREA_ID = aREA_ID;
	}

	public String getCHNL_NO() {
		return CHNL_NO;
	}

	public void setCHNL_NO(String cHNL_NO) {
		CHNL_NO = cHNL_NO;
	}

	public String getI_E_TYPE() {
		return I_E_TYPE;
	}

	public void setI_E_TYPE(String i_E_TYPE) {
		I_E_TYPE = i_E_TYPE;
	}

	public List<DEVICES> getDEVICES() {
		return DEVICES;
	}

	public void setDEVICES(List<DEVICES> dEVICES) {
		DEVICES = dEVICES;
	}

	@Override
	public String toString() {
		return "X24 [AREA_ID=" + AREA_ID + ", CHNL_NO=" + CHNL_NO + ", I_E_TYPE=" + I_E_TYPE + ", DEVICES=" + DEVICES
				+ "]";
	}

}
