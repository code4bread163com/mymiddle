package skywalkingserver;

import api.Product;
import api.ProductBaseService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by appe on 2018/5/29.
 */
@RestController
@EnableAutoConfiguration
@Slf4j
public class SampleController implements ProductBaseService {


    @RequestMapping("getProduct")
    @ResponseBody
    public String getProduct() {

        try {
            log.info("getProduct");
            Product product = new Product();
            return product.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return "Hello World!";
    }


}
