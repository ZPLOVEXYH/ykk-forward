package cn.samples.datareceiver.opsplatform.service.impl;

import cn.samples.datareceiver.opsplatform.entity.DataReceive;
import cn.samples.datareceiver.opsplatform.mapper.DataReceiveMapper;
import cn.samples.datareceiver.opsplatform.service.DataReceiveService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DataReceiveServiceImpl extends ServiceImpl<DataReceiveMapper, DataReceive> implements DataReceiveService {

    @Override
    public DataReceive selectDataReceiveById(Long id) {
        return this.baseMapper.selectById(id);
    }

}
