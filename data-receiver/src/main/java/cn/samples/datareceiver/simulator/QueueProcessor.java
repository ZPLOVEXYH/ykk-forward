package cn.samples.datareceiver.simulator;

import cn.samples.datareceiver.model.Area;
import cn.samples.datareceiver.model.Channel;
import cn.samples.datareceiver.model.DataPackage;
import cn.samples.datareceiver.model.Devices;
import cn.samples.datareceiver.utils.HttpUtil;
import cn.samples.datareceiver.utils.PackageUtil;
import cn.samples.datareceiver.utils.RedisUtil;
import cn.samples.datareceiver.xml.DEVICES;
import cn.samples.datareceiver.xml.X24;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QueueProcessor {

    @Value("${interface_url:http://localhost:8088/data/test}")
    private String URL;

    @Autowired
    RedisUtil redisUtil;

    private static BlockingQueue<MsgData> queue = new ArrayBlockingQueue(50);

    /**
     * 将消息放入blockingqueue队列
     *
     * @param data
     */
    public void enqueue(MsgData data) {
        try {
            queue.put(data);

//            dequeue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dequeue() {
        while (true) {
            MsgData data = null;
            try {
                data = (MsgData) queue.take();
                processData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理消息数据
     * @param data
     */
    private void processData(MsgData data) {
        if (null == data) {
            new Exception("参数为空");
        }

        DataPackage dp = data.getMsg();
        // 场站号
        String areaId = dp.getAreaId();
        // 通道号
        String chnlNo = dp.getChnlNo();
        X24 x24 = PackageUtil.getX24FromXml(dp.getXml());
        String cacheKey = areaId + "-" + chnlNo;
        List<DEVICES> devicesList =  x24.getDEVICES();
        Map<String, String> redisMap = redisUtil.getAllFromHashCache(cacheKey);
        // 设置变更记录的有效期30分钟
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

        log.debug("receive x{}", data.toString());
    }

    /**
     * 推送组装好的以场站为单元的24报文到运维平台
     */
    public void pushX24Msg() {
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

        String response = HttpUtil.sendPostToJson(URL, JSON.toJSONString(areaList));
        log.info("接口返回的内容为：{}", response);
    }

    @Slf4j
    @Getter
    @Setter
    @AllArgsConstructor
    public static class MsgData implements Serializable {
        private DataPackage msg;
        private String ip;
        private int port;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}