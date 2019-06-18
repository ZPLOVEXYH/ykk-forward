package cn.samples.datareceiver.opsplatform.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
