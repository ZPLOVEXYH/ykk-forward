package cn.samples.datareceiver.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 计时器
 *
 * @author yinheng
 */
public class Stopwatch implements Serializable {
    private static final long serialVersionUID = 1L;

    public static AtomicLong count = new AtomicLong(0);

    private long uuid;

    private long start;

    private Stopwatch() {
        this.start = System.currentTimeMillis();
        this.uuid = count.incrementAndGet();
    }

    public static Stopwatch createStarted() {
        return new Stopwatch();
    }

    public static long elapsed(long start) {
        return System.currentTimeMillis() - start;
    }

    public static String formatStart(long start) {
        Date startDate = new Date(start);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(startDate);
    }

    /**
     * 耗时，单位：毫秒
     *
     * @return
     */
    public long elapsed() {
        return elapsed(start);
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }
}
