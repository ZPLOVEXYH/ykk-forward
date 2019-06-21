package cn.samples.datareceiver.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class AREA implements Serializable {

    private String areaId;

    private List<X24> x24List;
}
