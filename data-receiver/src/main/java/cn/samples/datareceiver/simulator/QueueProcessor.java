package cn.samples.datareceiver.simulator;

import cn.samples.datareceiver.model.Area;
import cn.samples.datareceiver.model.Channel;
import cn.samples.datareceiver.model.DataPackage;
import cn.samples.datareceiver.model.Devices;
import cn.samples.datareceiver.utils.HttpUtil;
import cn.samples.datareceiver.utils.PackageUtil;
import cn.samples.datareceiver.utils.RedisUtil;
import cn.samples.datareceiver.xml.CHANNEL;
import cn.samples.datareceiver.xml.DEVICES;
import cn.samples.datareceiver.xml.X24;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
     *
     * @param data
     */
//    private void processData(MsgData data) {
//        if (null == data) {
//            new Exception("参数为空");
//        }
//
//        DataPackage dp = data.getMsg();
//        // 场站号
//        String areaId = dp.getAreaId();
//        // 通道号
//        String chnlNo = dp.getChnlNo();
//        X24 x24 = PackageUtil.getX24FromXml(dp.getXml());
//        String cacheKey = areaId + "-" + chnlNo;
//        List<DEVICES> devicesList = x24.getDevices();
//        Map<String, String> redisMap = redisUtil.getAllFromHashCache(cacheKey);
//        // 设置变更记录的有效期30分钟
//        redisUtil.setExpire("change-" + cacheKey, 10, TimeUnit.SECONDS);
//        devicesList.stream().forEach(devices -> {
//            // hashKey
//            String hashKey = devices.getDeviceId();
//            // hashValue
//            String hashValue = devices.getStatusValue() + "-" + devices.getErrCode();
//            String jsonStr = JSON.toJSONString(devices);
//            if (CollectionUtils.isEmpty(redisMap)) {
//                redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
//
//                redisUtil.saveToHashCache("change:" + cacheKey, hashKey, jsonStr);
//            } else {
//                // 查看哈希表 key 中，指定的字段是否存在
//                String hashV = redisUtil.getFromHashCache(cacheKey, hashKey);
//                if (StringUtils.isEmpty(hashV)) {
//                    redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
//                    redisUtil.saveToHashCache("change:" + cacheKey, hashKey, jsonStr);
//                } else {
//                    if (!hashV.equals(hashValue)) {
//                        redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
//                        redisUtil.saveToHashCache("change:" + cacheKey, hashKey, jsonStr);
//                    }
//                }
//            }
//        });
//
//        log.debug("receive x{}", data.toString());
//    }

    /**
     * 处理消息数据
     *
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
        String xml = dp.getXml();
        X24 x24 = PackageUtil.getX24FromXml(xml);
        String cacheKey = areaId + "-" + chnlNo;
        List<DEVICES> devicesList = x24.getDevices();
        Map<String, String> redisMap = redisUtil.getAllFromHashCache(cacheKey);
        devicesList.stream().forEach(devices -> {
            // hashKey
            String hashKey = devices.getDeviceId();
            // hashValue
            String hashValue = devices.getStatusValue() + "-" + devices.getErrCode();
            // 将设备对象转成jsonStr类型的
            String jsonStr = JSONObject.toJSONString(devices);
//            JSON.toJSONString(devices);
            log.info("jsonStr:{}", jsonStr);
            if (CollectionUtils.isEmpty(redisMap)) {
                redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
                redisUtil.saveToHashCache("device_info", cacheKey + ":" + hashKey, jsonStr);
            } else {
                // 查看哈希表 key 中，指定的字段是否存在
                String hashV = redisUtil.getFromHashCache(cacheKey, hashKey);
                if (StringUtils.isEmpty(hashV)) {
                    redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
                    redisUtil.saveToHashCache("device_info", cacheKey + ":" + hashKey, jsonStr);
                } else {
                    if (!hashV.equals(hashValue)) {
                        redisUtil.saveToHashCache(cacheKey, hashKey, hashValue);
                        redisUtil.saveToHashCache("device_info", cacheKey + ":" + hashKey, jsonStr);
                    }
                }
            }

            // 将x24对象转成jsonStr类型的
            CHANNEL channel = PackageUtil.getChannelFromXml(xml);
            // 判断通道关键设备的状态是否出现故障，如果出现故障，那么通道的状态也设置为故障
            String deviceCode = devices.getDeviceCode();
            // 如果1表示关键设备，那么获取设备的状态值判断
            if ("1".equals(deviceCode)) {
                // 获取关键设备的状态值，如果社保的状态值为故障状态，那么通道状态为故障状态
                int statusValue = devices.getStatusValue();
                if ("1".equals(statusValue)) {
                    channel.setChnlState("1");
                }
            }

            // 获取得到存放在redis当中的通道集合信息
            String channelJsonStr = JSON.toJSONString(channel);
            // 将通道信息存入到redis hash当中
            redisUtil.saveToHashCache("channel_info", cacheKey, channelJsonStr);
        });
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

    /**
     * 推送组装好的以场站为单元的24报文到运维平台
     */
    public void clockPushX24Msg() {
        // 根据redis hash key获取得到hash value里面的field和对应的value值
        Map<String, String> redisMap = redisUtil.getAllFromHashCache("device_info");
        // 根据场站号来合并key值
        Map<String, List<String>> map = redisMap.keySet().stream().collect(Collectors.groupingBy(key -> {
            String[] keys = key.split(":");
            return keys[0];
        }));

        // 根据条件判断更新map的value值
        map.entrySet().stream().forEach(value -> {
            List<String> deviceList = new ArrayList<>();
            value.getValue().stream().forEach(s -> {
                // 根据cachekey和hashkey到redis hash查询得到value值
                String deviceJsonStr = redisUtil.getFromHashCache("device_info", s);
                deviceList.add(deviceJsonStr);
            });

            value.setValue(deviceList);
        });

        map.entrySet().stream().forEach(x -> {
            log.info("key:{}, value:{}", x.getKey(), x.getValue().toString());
        });

        // 根据redis hash key获取得到hash value里面的field和对应的value值
        Map<String, String> channelRedisMap = redisUtil.getAllFromHashCache("channel_info");
        List<X24> x24List = new ArrayList<>();
        channelRedisMap.entrySet().stream().filter(y -> map.containsKey(y.getKey())).forEach(x -> {
            List<String> jsonStr = map.get(x.getKey());
            List<DEVICES> devices = new ArrayList<>();
            jsonStr.forEach(y -> {
                devices.add(JSON.parseObject(y, DEVICES.class));
            });

            log.info("devices:{}", devices.toString());

            String channelVal = x.getValue();
            X24 x24 = JSON.parseObject(channelVal, X24.class);
            x24.setDevices(devices);

            log.info("x24:{}", x24.toString());
            // 最近一次更新的时间戳
            Long lastUpdateTime = x24.getLastUpdateTime();
            // 当前的时间戳
            Long currentTime = System.currentTimeMillis();
            int gap = (int) ((currentTime - lastUpdateTime) / (1000 * 60));
            log.info("util.CountTimeGap 两个时间之间的分钟差gap为：{}", gap);
            if (gap > 30) {
                x24.setChnlState("1");
            }

            log.info("x24_2:{}", x24.toString());

            x24List.add(x24);
        });

        String jsonStr = JSON.toJSONString(x24List);
        log.info("jsonStr:{}", jsonStr);
        redisUtil.saveToHashCache("front_show", "area_channel", jsonStr);

        // 推动组合好的24报文到运维平台
        String response = HttpUtil.sendPostToJson(URL, JSON.toJSONString(x24List));
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