package cn.samples.datareceiver.controller;

import cn.samples.datareceiver.model.AreaNode;
import cn.samples.datareceiver.utils.RedisUtil;
import cn.samples.datareceiver.utils.Result;
import cn.samples.datareceiver.xml.X24;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/x24")
public class X24DataControlller {

    @Autowired
    RedisUtil redisUtil;

    @GetMapping(value = "/queryX24List")
    public Result queryX24List(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                               @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        String jsonStr = redisUtil.getFromHashCache("front_show", "area_channel");
        List<X24> x24List = JSON.parseArray(jsonStr, X24.class);

        return new Result(0, true, "ok", x24List);
    }

    /**
     * 查询得到场站集合
     *
     * @return
     */
    @GetMapping(value = "/queryAreaList")
    public Result queryAreaList() {
        String jsonStr = redisUtil.getFromCache("channel_info");
        List<AreaNode> areaNodeList = JSON.parseArray(jsonStr, AreaNode.class);

        return new Result(0, true, "ok", areaNodeList);
    }
}
