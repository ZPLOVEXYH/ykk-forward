package cn.samples.datareceiver.opsplatform.model;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class MsgData implements Serializable {
    private DataPackage msg;
    private String ip;
    private int port;

    public MsgData() {
    }

    public MsgData(DataPackage msg, String ip, int port) {
        this.msg = msg;
        this.ip = ip;
        this.port = port;
    }

    public DataPackage getMsg() {
        return msg;
    }

    public void setMsg(DataPackage msg) {
        this.msg = msg;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
