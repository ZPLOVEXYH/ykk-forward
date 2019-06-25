package cn.samples.datareceiver;

import cn.samples.datareceiver.netty.client.TCPClient;
import cn.samples.datareceiver.simulator.QueueProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataReceiverApplication implements CommandLineRunner {

    @Value("${x24.push.perid:30}")
    private int perid;

    @Autowired
    TCPClient tcpClient;

    @Autowired
    private QueueProcessor queueprocessor;

    public static void main(String[] args) {
        SpringApplication.run(DataReceiverApplication.class, args);
    }

//    @SuppressWarnings({"Convert2Lambda"})
//    @Bean
//    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
//        return new ApplicationListener<ApplicationReadyEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//                try {
//                    tcpClient.connect();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }


    @Override
    public void run(String... strings) throws Exception {
//        ExecutorService es = Executors.newFixedThreadPool(1);
//        es.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    log.error("启动netty socket服务，用于监听24报文...");
//                    tcpClient.connect();
//                    log.info("启动端口，成功...");
//                } catch (Exception ex) {
//                    log.error("Start BusinessReceiver error, e={}", ex);
//                }
//            }
//        });
//
//        // 启动卡口请求监听，接收解析报文，转发请求到mq
//        ExecutorService executorFixed = Executors.newFixedThreadPool(1);
//        executorFixed.execute(new Runnable() {
//            @Override
//            public void run() {
//                queueprocessor.dequeue();
//            }
//        });

        // 启动卡口请求监听，接收解析报文，转发请求到mq
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.error("启动netty socket服务，用于监听24报文...");
                    tcpClient.connect();
                    log.info("启动端口，成功...");
                } catch (Exception ex) {
                    log.error("Start BusinessReceiver error, e={}", ex);
                }
            }
        }, "DataReceiverThread").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                queueprocessor.dequeue();
            }
        }, "QueueProcessorThread").start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                queueprocessor.pushX24Msg();
//            }
//        }, "ScheduleProcessorThread").start();

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                queueprocessor.pushX24Msg();
//            }
//        }, 0, 30 * 60 * 1000);

//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
//                new BasicThreadFactory.Builder().namingPattern("pushX24Msg-schedule-pool-%d").daemon(true).build());
//        scheduleAtFixedRate ：是以上一个任务开始的时间计时，period时间过去后，检测上一个任务是否执行完毕，
//        如果上一个任务执行完毕，则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行
//        第一次调度开始时间点=当前时间+initialDelay
//        第二次调度开始时间点=initialDelay + perid
//        第n次调度开始时间点=initialDelay + n * perid

//        log.info("间隔{}分钟推送：", perid);
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
////                queueprocessor.pushX24Msg();
//                queueprocessor.clockPushX24Msg();
//            }
//        }, perid, perid, TimeUnit.MINUTES);
    }
}
