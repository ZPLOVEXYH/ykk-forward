package cn.samples.datareceiver.opsplatform.controller;

import cn.samples.datareceiver.opsplatform.model.Area;
import cn.samples.datareceiver.opsplatform.model.X24;
import cn.samples.datareceiver.opsplatform.service.DataReceiveService;
import cn.samples.datareceiver.opsplatform.service.X24Service;
import cn.samples.datareceiver.opsplatform.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "data", description = "运维平台接收转发平台推送的报文")
@RestController
@RequestMapping("/data")
public class DataReceiveController {

//    @PostMapping(value = "/test", consumes = "application/json; cahrset=utf-8")
//    public String testDataReceive(@RequestBody MsgData msgData) {
//        log.info("打印的消息内容为：" + msgData.toString());
//        DataPackage dp = msgData.getMsg();
//        String messageType = Integer.toHexString(dp.getMessageType());
//        String ip = msgData.getIp();
//        String areaId = dp.getAreaId();
//        String chnlNo = dp.getChnlNo();
//        String xml = dp.getXml();
//
//        log.debug("receive x{} from {}, areaId:{}, chnlNo:{}, xml:{}", messageType, ip, areaId, chnlNo, xml);
//        return "success";
//    }

//    @Autowired
//    DataReceiveService dataReceiveService;

//    @ApiOperation("接收并处理转发平台推送的报文")
//    @PostMapping(value = "/test", consumes = "application/json; cahrset=utf-8")
//    public Result testDataReceive(@RequestBody List<Area> areaList) {
//        log.info("打印的消息内容为：{}" + areaList.toString());
//        areaList.stream().forEach(area -> {
//            String areaId = area.getAreaId();
//            log.info("场站号：{}", areaId);
//            List<Channel> channels = area.getChannelList();
//            channels.stream().forEach(channel -> {
//                String channelNo = channel.getChnlNo();
//                log.info("通道号：{}", channelNo);
//                List<Devices> devicesList = channel.getDevicesList();
//                devicesList.stream().forEach(devices -> {
//                    log.info("设备信息：{}", devices.toString());
//                });
//            });
//        });
//
//        DataReceive dataReceive = new DataReceive();
//        dataReceive.setXmlString(areaList.toString());
//
//        boolean insertBool = dataReceiveService.insert(dataReceive);
//        if(insertBool) {
//            log.info("插入成功：{}", insertBool);
//        } else {
//            log.info("插入失败：{}", insertBool);
//        }
//
//        return new Result(0, "success", dataReceiveService.selectDataReceiveById(1L));
//
////        DataPackage dp = msgData.getMsg();
////        String messageType = Integer.toHexString(dp.getMessageType());
////        String ip = msgData.getIp();
////        String areaId = dp.getAreaId();
////        String chnlNo = dp.getChnlNo();
////        String xml = dp.getXml();
////
////        log.debug("receive x{} from {}, areaId:{}, chnlNo:{}, xml:{}", messageType, ip, areaId, chnlNo, xml);
//    }


    @Autowired
    X24Service x24Service;

    @Autowired
    DataReceiveService dataReceiveService;

    @ApiOperation("接收并处理转发平台推送的报文")
    @PostMapping(value = "/test", consumes = "application/json; cahrset=utf-8")
    public Result testDataReceive(@RequestBody List<Area> areaList) {
        log.info("打印的消息内容为：{}" + areaList.toString());

        areaList.stream().forEach(x -> {
            boolean x24Insert = x24Service.insertBatch(x.getX24List());
            log.info("批量插入x24到数据库：{}", x24Insert);

            List<X24> x24List = x.getX24List();
            x24List.stream().forEach(y -> {
                y.getDevices().stream().forEach(t -> t.setChnlNo(y.getChnlNo()));
                boolean deviceInsert = dataReceiveService.insertBatch(y.getDevices());
                log.info("批量插入设备到数据库：{}", deviceInsert);
            });
        });

        return new Result(0, "success");

    }
}
