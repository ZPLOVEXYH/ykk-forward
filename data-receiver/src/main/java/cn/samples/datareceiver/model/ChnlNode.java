package cn.samples.datareceiver.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ChnlNode implements Serializable {

    private String areaId;
    private String chnlNo;
    private String chnlName;
    private String ieType;
    private int isenable = 0; // 1、enable 0、disable
    private String state; // 通道状态：
    private String loginState; // 登录状态
    private String chnlIp;
    private List<InterChnlDevice> devices;
    private Short cpuPct;
    private Long memSize;
    private Long memUsage;
    private Long diskSize;
    private Long diskUsage;
    private Date ctime;
}
