package cn.samples.datareceiver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class Devices implements Serializable {

    private String deviceId;

    private String deviceJsonStr;

}
