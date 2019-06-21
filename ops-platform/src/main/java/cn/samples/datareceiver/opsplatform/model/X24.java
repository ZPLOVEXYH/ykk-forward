package cn.samples.datareceiver.opsplatform.model;

import cn.samples.datareceiver.opsplatform.model.old.DEVICES;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 0x24 卡口设备状态信息（XMLInfoDeviceStatus）
 *
 * @author yinheng
 */
@Setter
@Getter
@ToString
@TableName("tb_data_x24")
public class X24 /* extends BaseModel */ implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 场站编号（10位）
    @TableField(value = "area_id")
    protected String areaId;

    // 通道编号（10位）
    @TableField(value = "chnl_no")
    protected String chnlNo;

    // 通道状态：未登陆、正常（登陆）、注销、故障、告警
    @TableField(value = "chnl_state")
    protected String chnlState;

    // 最近一次的更新时间
    @TableField(value = "last_update_time")
    protected Long lastUpdateTime;

    // I表示进卡口，O（字母）表示出卡口
    @TableField(value = "i_e_type")
    protected String ieType;

    // 更新时间
    @TableField(value = "update_time")
    protected Long updateTime = System.currentTimeMillis();

    // 如果卡口没有此设备，则相应的标签就不存在
    @TableField(exist = false)
    private List<DEVICES> devices;

}
