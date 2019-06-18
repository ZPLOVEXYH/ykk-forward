package cn.samples.datareceiver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class Area implements Serializable {

    /**
     * 场站号
     */
    private String areaId;

    /**
     * 进出标志
     */
    private String ieFlag;

    /**
     * 通道集合
     */
    private List<Channel> channelList;
}
