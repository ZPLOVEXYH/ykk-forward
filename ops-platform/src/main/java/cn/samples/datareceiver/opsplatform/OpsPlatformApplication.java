package cn.samples.datareceiver.opsplatform;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
// 扫描dao或者是Mapper接口
@MapperScan("cn.samples.datareceiver.opsplatform.mapper*")
public class OpsPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpsPlatformApplication.class, args);
    }

}
