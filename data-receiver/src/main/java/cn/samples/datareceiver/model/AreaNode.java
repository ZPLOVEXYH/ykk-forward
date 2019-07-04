package cn.samples.datareceiver.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AreaNode implements Serializable {

    private String areaId;
    private String areaName;
    private String customsCode;
    private String customsName;
    private Double lon, lat; // 经纬度
    private List<ChnlNode> chnls;
    private boolean monitor = true;
}
