package cn.samples.datareceiver.opsplatform.model;

/**
 * 目的地<br/>
 * SENDTO：None = 0, //没有发送角色(不发送）<br/>
 * CheckPost = 1, //转发前端卡口 <br/>
 * Platform = 2, //转发平台 <br/>
 * ReplyCheckPost = 3 //回复卡口
 *
 * @author yinheng
 */
public enum SendTo {
    None(0), // 没有发送角色(不发送）
    CheckPost(1), // 转发前端卡口
    Platform(2), // 转发平台
    ReplyCheckPost(3); // 回复卡口

    private int _value;

    private SendTo(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }
}
