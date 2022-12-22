package skywalking;

/**
 * @author zhangliang
 * @date 2020/10/6
 */

import api.ProductBaseService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

//name 为product项目中application.yml配置文件中的application.name;
//path 为product项目中application.yml配置文件中的context.path;
@FeignClient(name = "skywalking-server")//
//@Componet注解最好加上，不加idea会显示有错误，但是不影响系统运行；
@Component
public interface ProductService extends ProductBaseService {

}