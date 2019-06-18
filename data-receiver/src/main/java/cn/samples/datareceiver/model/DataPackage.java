package cn.samples.datareceiver.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * 数据报文类
 */
public class DataPackage implements Serializable {
    private static final long serialVersionUID = -7744187799749096342L;

    /**
     * 报文总长
     */
    private int size;

    /**
     * 消息类型
     */
    private byte messageType;

    /**
     * 场站号
     */
    private String areaId;

    /**
     * 通道号
     */
    private String chnlNo;

    /**
     * 序列号
     */
    private String seqNo;

    /**
     * 进出标志
     */
    private String ieFlag;

    /**
     * 标识符
     */
    private int mark;

    /**
     * xml报文长度
     */
    private int xmlSize;
    /**
     * xml报文
     */
    private String xml;
    /**
     * xml报文流
     */
    private byte[] xmlBytes = null;

    /**
     * 图片大小
     */
    private int picSize = 0;
    /**
     * 图片
     */
    private List<ContaPicture> contaPics = null;

    private byte[] allBytes = null;// full bytes from HEAD to TAIL

    /**
     * 向卡口|平台发送消息后是否要回调处理（入MessageQueue.SEND_CALLBACK队列）
     */
    private boolean needCallback = false;

    /**
     * 发送到，默认不发送
     */
    private SendTo sendTo = SendTo.None;

    /**
     * 是否发送成功
     */
    private boolean sendResult = false;

    private int retry = 0; // 发送重试次数

    private Receiver receiver = null; // 发送回调时用到，用于保存发送结果（重试的receiver通过消息head传递）

    private Stopwatch timer; // 计时器，统计用

    /**
     * 是否包含图片
     *
     * @return
     */
    public boolean hasPics() {
        return null != contaPics && contaPics.size() > 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
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

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIeFlag() {
        return ieFlag;
    }

    public void setIeFlag(String ieFlag) {
        this.ieFlag = ieFlag;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getXmlSize() {
        return xmlSize;
    }

    public void setXmlSize(int xmlSize) {
        this.xmlSize = xmlSize;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getPicSize() {
        return picSize;
    }

    public void setPicSize(int picSize) {
        this.picSize = picSize;
    }

    public List<ContaPicture> getContaPics() {
        return contaPics;
    }

    public void setContaPics(List<ContaPicture> contaPics) {
        this.contaPics = contaPics;
    }

    public byte[] getXmlBytes() {
        return xmlBytes;
    }

    public void setXmlBytes(byte[] xmlBytes) {
        this.xmlBytes = xmlBytes;
    }

    public byte[] getAllBytes() {
        return allBytes;
    }

    public void setAllBytes(byte[] allBytes) {
        this.allBytes = allBytes;
    }

    public boolean isNeedCallback() {
        return needCallback;
    }

    public void setNeedCallback(boolean needCallback) {
        this.needCallback = needCallback;
    }

    /**
     * 0000000000，广播
     *
     * @return
     */
    @JsonIgnore
    public boolean isBroadcast() {
        return "0000000000".equals(areaId) && "0000000000".equals(chnlNo);
    }

    public boolean isSendResult() {
        return sendResult;
    }

    public void setSendResult(boolean sendResult) {
        this.sendResult = sendResult;
    }

    public SendTo getSendTo() {
        return sendTo;
    }

    public void setSendTo(SendTo sendTo) {
        this.sendTo = sendTo;
    }

    /**
     * @return the retry
     */
    public int getRetry() {
        return retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }

    /**
     * @return the receiver
     */
    public Receiver getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Stopwatch getTimer() {
        return timer;
    }

    public void setTimer(Stopwatch timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
