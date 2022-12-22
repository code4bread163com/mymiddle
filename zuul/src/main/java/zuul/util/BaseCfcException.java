package zuul.util;


/**
 * 业务异常。
 *
 * @author linjun
 * @date 2020年11月18日
 */
public abstract class BaseCfcException extends RuntimeException  {

    /**
     * Instantiates a new Acc tra exception.
     */
    public BaseCfcException() {
        super();
    }

    /**
     * Instantiates a new Acc tra exception.
     *
     * @param message the message
     */
    public BaseCfcException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Acc tra exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BaseCfcException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Acc tra exception.
     *
     * @param cause the cause
     */
    public BaseCfcException(Throwable cause) {
        super(cause);
    }

    /**
     * 获取错误码。建议每个子类都定义特定的错误码。
     *
     * @return the ret code
     */
    public abstract String getRetCode();

    /**
     * 获取错误提示信息。建议子类提供详细的错误说明
     *
     * @return the ret msg
     */
    public abstract String getRetMsg();

    public final String getCode() {
        return getRetCode();
    }

    public final String getMsg() {
        return getRetMsg();
    }

    /**
     * 要求子类提供包含上下文的异常信息
     *
     * @return 异常信息
     */
    @Override
    public abstract String getLocalizedMessage();
}
