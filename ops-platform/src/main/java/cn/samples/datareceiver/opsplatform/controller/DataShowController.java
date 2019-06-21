package cn.samples.datareceiver.opsplatform.controller;

import cn.samples.datareceiver.opsplatform.model.X24;
import cn.samples.datareceiver.opsplatform.model.old.DEVICES;
import cn.samples.datareceiver.opsplatform.service.DataReceiveService;
import cn.samples.datareceiver.opsplatform.service.X24Service;
import cn.samples.datareceiver.opsplatform.util.Result;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "front", description = "提供接口给前端")
@RestController
@RequestMapping(value = "/front")
public class DataShowController {

    @Autowired
    X24Service x24Service;

    @Autowired
    DataReceiveService dataReceiveService;

    @ApiOperation("提供接口给前端")
    @GetMapping(value = "/queryX24List")
    public Result queryX24List(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<X24> x24List = x24Service.selectList(new EntityWrapper<>());
        PageInfo<X24> pageInfo = new PageInfo<>(x24List);

        return new Result(0, "success", pageInfo);
    }

    @ApiOperation("根据通道编号返回设备集合给前端")
    @GetMapping(value = "/queryDevicesListByChnlNo/{chnlNo}")
    public Result queryChannelDevicesList(@PathVariable String chnlNo,
                                          @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("chnl_no", chnlNo);
        List<DEVICES> dataReceiveList = dataReceiveService.selectList(entityWrapper);
        PageInfo<DEVICES> pageInfo = new PageInfo<>(dataReceiveList);

        return new Result(0, "success", pageInfo);
    }
}
