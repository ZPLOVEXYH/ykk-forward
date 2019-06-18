package cn.samples.datareceiver.opsplatform.service;

import cn.samples.datareceiver.opsplatform.entity.DataReceive;
import com.baomidou.mybatisplus.service.IService;

public interface DataReceiveService extends IService<DataReceive> {

    DataReceive selectDataReceiveById(Long id);
}
