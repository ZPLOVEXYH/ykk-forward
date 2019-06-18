package cn.samples.datareceiver.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 消息接受者（卡口或平台）
 */
public class Receiver implements Serializable {
    private static final long serialVersionUID = -6737592686669034551L;

    private long receId;
    private String ip;
    private int port;
    private String name;

    private String areaId; // 场站号
    private String chnlNo; // 通道号

    public long getReceId() {
        return receId;
    }

    public void setReceId(long receId) {
        this.receId = receId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Receiver(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getChnlNo() {
        return chnlNo;
    }

    public void setChnlNo(String chnlNo) {
        this.chnlNo = chnlNo;
    }

    public Receiver() {
    }

    public Receiver(String areaId, String chnlNo, String ip) {
        this.ip = ip;
        this.areaId = areaId;
        this.chnlNo = chnlNo;
    }

    public Receiver(String areaId, String chnlNo, String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.areaId = areaId;
        this.chnlNo = chnlNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
