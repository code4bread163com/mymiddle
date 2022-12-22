package zuul.exception;

import com.netflix.zuul.exception.ZuulException;

/**
 /**
 * 自定义网关异常
 *
 * 至于为什么要继承 {@link ZuulException}
 *
 * 可以参考 {@link com.netflix.zuul.FilterProcessor#processZuulFilter(ZuulFilter)} 方法中的异常处理
 *
 * @author zhangliang
 * @date 2021/11/11
 */
public class GatewayException extends ZuulException {

    public GatewayException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getMessage(), apiResponseCode.getCode(),
                apiResponseCode.getMessage());
    }

    public GatewayException(int code, String message) {
        super(message, code, message);
    }

}
