//package cn.samples.datareceiver.netty;
//
//import io.netty.channel.Channel;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//@Component
//public class ChannelRepository {
//    private ConcurrentMap<String, Channel> channelCache = new ConcurrentHashMap<>();
//
//    public ChannelRepository put(String key, Channel value) {
//        channelCache.put(key, value);
//        return this;
//    }
//
//    public Channel get(String key) {
//        return channelCache.get(key);
//    }
//
//    public void remove(String key) {
//        this.channelCache.remove(key);
//    }
//
//    public int size() {
//        return this.channelCache.size();
//    }
//}
