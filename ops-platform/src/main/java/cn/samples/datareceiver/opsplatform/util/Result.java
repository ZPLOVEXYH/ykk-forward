package cn.samples.datareceiver.opsplatform.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    public static final Integer OK = 0;
    public static final Integer Error = -1;

    private Integer code;
    private String msg;
    private T data;

    public Result(){
        this.code = OK;
        this.msg = "success";
    }

    public Result(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg, T data) {
        super();
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