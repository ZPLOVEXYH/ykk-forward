package cn.samples.datareceiver.opsplatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppProfile {
    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PROD = "prod";

    @Autowired
    private Environment environment;

    public boolean isDev() {
        return environment.acceptsProfiles(DEV);
    }

    public boolean isTest() {
        return environment.acceptsProfiles(TEST);
    }

    public boolean isProd() {
        return environment.acceptsProfiles(PROD);
    }

}
