package cn.samples.datareceiver.model;

import java.io.Serializable;

public class ContaPicture implements Serializable {

    private static final long serialVersionUID = 7161347360910684248L;

    public byte getPicType() {
        return picType;
    }

    public String getHexPicType() {
        return Integer.toHexString(picType);
    }

    public void setPicType(byte picType) {
        this.picType = picType;
    }

    public byte[] getPictureBytes() {
        return pictureBytes;
    }

    public void setPictureBytes(byte[] pictureBytes) {
        this.pictureBytes = pictureBytes;
    }

    /**
     * @return the seqNo
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * 图片类型（值）
     */
    private byte picType;
    /**
     * 图片流
     */
    private byte[] pictureBytes;

    /**
     * 序列号
     */
    private String seqNo;
}
