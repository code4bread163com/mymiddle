package zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author zhangliang
 * @date 2021/11/10
 */
@Slf4j
//@Component
public class CustomSendErrorFilter extends ZuulFilter {
    private String errorPath = "/error";


    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private final ErrorAttributes errorAttributes;

    @Autowired
    public CustomSendErrorFilter(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        try {
            int responseStatusCode = ctx.getResponseStatusCode();
            // 此处自定义响应体start
            WebRequest webRequest = new ServletWebRequest(request);
            Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, true);

            String msg = errorAttributes.getOrDefault("error", "not found").toString();
            String code = errorAttributes.getOrDefault("status", 404).toString();
            String cumstomBody = "{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":\"\"}";

            // 此处自定义响应体end
            response.setStatus(ctx.getResponseStatusCode());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream().write(cumstomBody.getBytes());

        } catch (IOException e) {
            ReflectionUtils.rethrowRuntimeException(e);
        } finally {
            //ThreadLocalUtil.remove();
        }
        return null;
//        RequestContext ctx = RequestContext.getCurrentContext();
//        Throwable throwable = ctx.getThrowable();
//        log.error("this is a errorFilter: {}", throwable.getCause().getMessage());
//
//        HttpServletRequest request = ctx.getRequest();
//        if (request.getAttribute("javax.servlet.error.errorCode") == null) {
//            request.setAttribute("javax.servlet.error.errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        if (request.getAttribute("javax.servlet.error.errorMessage") == null) {
//                request.setAttribute("javax.servlet.error.errorMessage", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//            }
//
//            RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
//            if (dispatcher != null) {
//                if (!ctx.getResponse().isCommitted()) {
//                try {
//                    dispatcher.forward(request, ctx.getResponse());
//                } catch (ServletException ex) {
//                    log.error("", ex);
//                } catch (IOException ex) {
//                    log.error("", ex);
//                    ReflectionUtils.rethrowRuntimeException(ex);
//                }
//            }
//        }
//        return null;
    }
}
