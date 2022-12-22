package zuul.limit.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;
import zuul.limit.ExceptionUtil;

/**
 * @author zhangliang
 * @date 2022/11/25
 */
@Service
public class TestServiceImpl  {

    public String temp() {
        return String.valueOf(System.currentTimeMillis());
    }

    @SentinelResource(value = "test1", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    //@SentinelResource(value = "test1", fallback = "test2Fallback")
    public void test1() {
        System.out.println("Test1");
    }

    @SentinelResource(value = "test2", fallback = "test2Fallback")
    public String test2(long s) {
        if (s < 0) {
            throw new IllegalArgumentException("invalid arg");
        }
        return String.valueOf(System.currentTimeMillis());
    }

    @SentinelResource(value = "test3", defaultFallback = "test3Fallback",
            exceptionsToIgnore = {IllegalStateException.class})
    public String test3(String name) {
        if (name == null || "bad".equals(name)) {
            throw new IllegalArgumentException("oops");
        }
        if ("foo".equals(name)) {
            throw new IllegalStateException("oops");
        }
        return "Hello, " + name;
    }

    public String test2Fallback(long s, Throwable ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at test2" + s;
    }

    public String test3Fallback() {
        System.out.println("Go to test3 fallback");
        return "test3_fallback";
    }
}
