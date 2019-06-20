package cn.samples.datareceiver.opsplatform.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "area")
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
