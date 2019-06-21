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
     * 通道集合
     */
    private List<X24> x24List;
}
