package api;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangliang
 * @date 2020/12/21
 */
public interface ProductBaseService1 {
    @RequestMapping(value = "getProduct1")
    String getProduct1();
}
