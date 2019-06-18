package cn.samples.datareceiver;

import cn.samples.datareceiver.model.Area;
import cn.samples.datareceiver.model.Channel;
import cn.samples.datareceiver.model.Devices;
import cn.samples.datareceiver.utils.HttpUtil;
import cn.samples.datareceiver.utils.PackageUtil;
import cn.samples.datareceiver.utils.RedisUtil;
import cn.samples.datareceiver.xml.DEVICES;
import cn.samples.datareceiver.xml.X24;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataReceiverApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        String xml = "<DEVICE_STATUS AREA_ID=\"0000000000\"  CHNL_NO=\"2222222222\"  I_E_TYPE=\"I\"><DEVICES><DEVICE_ID>170000</DEVICE_ID><DEVICE_NAME>LED</DEVICE_NAME><STATUS_VALUE>2</STATUS_VALUE><ERR_CODE>1</ERR_CODE></DEVICES><DEVICES><DEVICE_ID>180000</DEVICE_ID><DEVICE_NAME>LED</DEVICE_NAME><STATUS_VALUE>0</STATUS_VALUE><ERR_CODE>1</ERR_CODE></DEVICES></DEVICE_STATUS>";
        // 场站号
        String areaId = "0000000000";
        // 通道号
        String chnlNo = "2222222222";
        X24 x24 = PackageUtil.getX24FromXml(xml);
        String cacheKey = areaId + "-" + chnlNo;
        List<DEVICES> devicesList =  x24.getDEVICES();
        Map<String, String> redisMap = redisUtil.getAllFromHashCache(cacheKey);
        // 设置变更记录的有效期30分钟
//        redisUtil.setExpire("change-" + cacheKey, 30, TimeUnit.MINUTES);
        redisUtil.setExpire("change-" + cacheKey, 10, TimeUnit.SECONDS);
        devicesList.stream().forEach(devices -> {
            // hashKey
            String hashKey = devices.getDEVICE_ID();
            // hashValue
            String hashValue = devices.getSTATUS_VALUE() + "-" + devices.getERR_CODE();
            String jsonStr = JSON.toJSONString(devices);
            if(CollectionUtils.isEmpty(redisMap)){
                redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);

                redisUtil.saveToHashCache("change:" + cacheKey, hashKey, jsonStr);
            } else {
                // 查看哈希表 key 中，指定的字段是否存在
                String hashV = redisUtil.getFromHashCache(cacheKey, hashKey);
                if(StringUtils.isEmpty(hashV)) {
                    redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
                    redisUtil.saveToHashCache("change:" + cacheKey, hashKey, jsonStr);
                } else {
                    if(!hashV.equals(hashValue)) {
                        redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
                        redisUtil.saveToHashCache("change:" + cacheKey, hashKey, jsonStr);
                    }
                }
            }
        });
    }

    @Test
    public void getRedisDataTransToPlatform(){
        // 根据redis keys 通配符获取得到所有的key值
        Set<String> keySets = redisUtil.getKeyByPattern("change*");
        // 根据场站号来合并key值
        Map<String, List<String>> map = keySets.stream().collect(Collectors.groupingBy(key -> {
            String[] keys = key.split("-");
            return keys[0];
        }));

        List<Area> areaList = new ArrayList<>();
        map.entrySet().stream().forEach(x -> {
            Area area = new Area();
            area.setAreaId(x.getKey().split(":")[1]);

            List<Channel> channelList = new ArrayList<>();
            List<String> strList = x.getValue();
            strList.stream().forEach(cacheKey -> {
                Channel channel = new Channel();
                channel.setChnlNo(cacheKey.split("-")[1]);

                List<Devices> devicesList = new ArrayList<>();

                Map<String, String> redisMap = redisUtil.getAllFromHashCache(cacheKey);
                redisMap.entrySet().stream().forEach(field -> {
                    Devices devices = new Devices();
                    devices.setDeviceId(field.getKey());
                    devices.setDeviceJsonStr(field.getValue());

                    devicesList.add(devices);
                });

                channel.setDevicesList(devicesList);
                channelList.add(channel);
            });

            area.setChannelList(channelList);
            areaList.add(area);
        });


        String response = HttpUtil.sendPostToJson("http://localhost:8088/data/test", JSON.toJSONString(areaList));
        log.info("接口返回的内容为：{}", response);
    }
}






