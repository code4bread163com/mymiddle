package zuul.bussfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import zuul.util.JsonUtils;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务交易时间窗口检验过滤器
 *
 * @author zhangliang
 * @date 2021/11/11
 */
//@Component
public class BusinessForbidFilter extends ZuulFilter {

    /**
     * 当前时间
     */
    private static final LocalTime NOW = LocalTime.now();

    /**
     * 零点
     */
    private static final LocalTime ZERO_CLOCK = LocalTime.of(19, 0);

    /**
     * 二十点
     */
    private static final LocalTime TWENTY_CLOCK = LocalTime.of(20, 0);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {




        if (NOW.isAfter(ZERO_CLOCK) && NOW.isBefore(TWENTY_CLOCK)) {
            // 如果用户在0-20点之间访问了系统，则异常
            //zool 使用时，上下文对象RequestContext，是共享的。 所以通过RequestContext 获取值
            RequestContext requestContext = RequestContext.getCurrentContext();

            // 构造返回报文
            Map<String, Object> result = new HashMap<>();
            result.put("code", HttpStatus.SC_FORBIDDEN);
            result.put("message", "FORBIDDEN");

            //不会被zuul路由转发，也就是不会请求到后端具体的服务。但是如果当前filter后面还有其他filter的话，其他filter依然会执行
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_FORBIDDEN);
            requestContext.setResponseBody(JsonUtils.toString(result));
        }

        return null;
    }

}
