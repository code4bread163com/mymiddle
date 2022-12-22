package zuul.controller;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangliang
 * @date 2021/11/11
 */
//@RestController
@RequestMapping("/error")
public class GatewayErrorController implements ErrorController {

    /**
     * zuul的异常处理，当网关出现问题进入异常处理
     *
     * @param request HTTP请求
     * @return API统一响应
     */
    @RequestMapping
    public ApiResponse error(HttpServletRequest request, HttpServletResponse response) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        String message = "服务器内部错误";

        if (exception instanceof ZuulException) {
            message = exception.getMessage();
        }

        response.setStatus(HttpStatus.OK.value());

        return new ApiResponse(code, message);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
