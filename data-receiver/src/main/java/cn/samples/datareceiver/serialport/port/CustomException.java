package cn.samples.datareceiver.serialport.port;

public class CustomException extends RuntimeException {

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    /**
     * @param message 需要显示的异常信息
     * @param cause   为Throwable或其子类的实例
     */
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
