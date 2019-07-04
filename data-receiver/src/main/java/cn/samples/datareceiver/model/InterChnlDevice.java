package cn.samples.datareceiver.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
@Setter
@Getter
public class InterChnlDevice implements Serializable {
    @JSONField(name = "DEVICE_ID")
    private String deviceId;

    @JSONField(name = "DEVICE_NAME")
    private String deviceName;

    @JSONField(name = "AREA_ID")
    private String areaId;

    @JSONField(name = "CHNL_NO")
    private String chnlNo;

    @JSONField(name = "DEVICE_STATUS")
    private Integer status; // 0：正常 1：停用 2：故障

    private String errcode;

    // 错误描述
    private String errDesc;

    private Date occurtime;

    private String areaName;
    private String chnlName;
}