package product.controller;

import api.Product;
import api.ProductBaseService1;
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
public class SampleController1 implements ProductBaseService1 {
    private static final Logger logger = LoggerFactory.getLogger(SampleController1.class);


    @RequestMapping("getProduct1")
    @ResponseBody
    public String getProduct1() {

        try {
              Product product = new Product();
              product.setAdd("d");
            return product.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return "Hello World!";
    }

}
