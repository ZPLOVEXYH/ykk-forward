package cn.samples.datareceiver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class Channel implements Serializable {

    /**
     * 通道号
     */
    private String chnlNo;

    /**
     * 设备集合
     */
    private List<Devices> devicesList;
}
