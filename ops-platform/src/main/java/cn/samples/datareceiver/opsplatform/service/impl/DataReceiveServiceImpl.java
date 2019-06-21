package cn.samples.datareceiver.opsplatform.service.impl;

import cn.samples.datareceiver.opsplatform.mapper.DevicesMapper;
import cn.samples.datareceiver.opsplatform.model.old.DEVICES;
import cn.samples.datareceiver.opsplatform.service.DataReceiveService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DataReceiveServiceImpl extends ServiceImpl<DevicesMapper, DEVICES> implements DataReceiveService {

}
