package cn.samples.datareceiver.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    public static final Integer OK = 0;
    public static final Integer Error = -1;

    private Integer code;
    private boolean success;
    private String msg;
    private T data;

    public Result() {
        this.code = OK;
        this.success = true;
        this.msg = "OK";
    }

    public Result(Integer code, boolean success, String msg) {
        super();
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public Result(Integer code, boolean success, String msg, T data) {
        super();
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}