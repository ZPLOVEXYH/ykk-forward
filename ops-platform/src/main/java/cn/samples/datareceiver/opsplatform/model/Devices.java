package cn.samples.datareceiver.opsplatform.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Devices implements Serializable {

    private String deviceId;

    private String deviceJsonStr;

}
