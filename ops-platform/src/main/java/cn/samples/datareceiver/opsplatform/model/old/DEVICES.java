package cn.samples.datareceiver.opsplatform.model.old;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@TableName("tb_data_devices")
public class DEVICES implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 通道编号
    @TableField(value = "chnl_no")
    private String chnlNo;

    // 设备模块ID，前端采集软件（默认ID为“00000000”）
    @TableField(value = "device_id")
    private String deviceId;

    // 设备名称
    @TableField(value = "device_name")
    private String deviceName;

    // 0：禁用 1：正常 2：故障 3：启用
    @TableField(value = "status_value")
    private Integer statusValue;

    // 故障代码
    @TableField(value = "err_code")
    private String errCode;

    // 日期字段json格式化操作
    @TableField(value = "occurtime")
    private Long occurtime;

    // CPU占用大小，百分比（最大100%）
    @TableField(value = "sys_cpu")
    private Byte sysCpu;

    // 内存大小
    @TableField(value = "mem_all")
    private Long memAll;

    // 内存占用情况
    @TableField(value = "mem_data")
    private long memData;

    // 磁盘大小
    @TableField(value = "disk_size")
    private Long diskSize;

    // 磁盘占用情况
    @TableField(value = "disk_data")
    private Long diskData;

    // 关键设备标识（0非关键设备，1关键设备）
    @TableField(value = "is_key_dev")
    private String isKeyDev;

    // 更新时间
    @TableField(value = "update_time")
    protected Long updateTime = System.currentTimeMillis();
}
