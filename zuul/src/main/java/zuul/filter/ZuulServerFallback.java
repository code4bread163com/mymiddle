package zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * zuul处理发生异常时，用这个东西来处理响应报文
 *
 * @author linjun
 */
@Component
public class ZuulServerFallback implements FallbackProvider {

    /**
     * 出错时的返回报文
     */
    public static final String ERROR_RESPONSE_BODY = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
            "<response><head>" +
            "<ec>E41004999</ec>" +
            "<em>网关请求目标服务出错</em>" +
            "</head<body></body></response>";
    private static final Logger log = LoggerFactory.getLogger(ZuulServerFallback.class);
    private static final byte[] ERROR_RESPONSE_BYTES = ERROR_RESPONSE_BODY.getBytes(StandardCharsets.UTF_8);

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            log.warn("route:{}, reason {}", route, reason, cause.getCause());
        }
        return fallbackResponse(route);
    }

    private ClientHttpResponse fallbackResponse(String route) {

        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() {
                log.info("熔断响应, route:{}", route);
                return new ByteArrayInputStream(ERROR_RESPONSE_BYTES);
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/xml;charset=UTF-8");
                return headers;
            }
        };
    }

}
