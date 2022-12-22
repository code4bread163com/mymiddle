package zuul.controller;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/11/10
 */
//@RestController
@RequestMapping({"${error.path}"})
public class ErrorHandler implements ErrorController {
    @Value("${error.path}")
    private String errorPath;

    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }

//    @RequestMapping
//    @ResponseBody
//    public String error(HttpServletRequest request) {
//        WebRequest webRequest = new ServletWebRequest(request);
//        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, true);
//        String msg = errorAttributes.getOrDefault("error", "not found").toString();
//        String code = errorAttributes.getOrDefault("status", 404).toString();
//        return "{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":\"\"}";
//    }

    @RequestMapping
    @ResponseBody
    public ZuulException error(HttpServletRequest request, HttpServletResponse response) {
        ZuulException ex = new ZuulException("null",
                Integer.parseInt(request.getAttribute("javax.servlet.error.errorCode").toString()),
                request.getAttribute("javax.servlet.error.errorMessage").toString());
        response.setStatus(ex.nStatusCode);
        return ex;
    }
}

