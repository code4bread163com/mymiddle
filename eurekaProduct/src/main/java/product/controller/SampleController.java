package product.controller;

import api.Product;
import api.ProductBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by appe on 2018/5/29.
 */
@RestController
@EnableAutoConfiguration
public class SampleController implements ProductBaseService {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);


    @RequestMapping("getProduct")
    @ResponseBody
    public String getProduct() {

        try {
              Product product = new Product();
            return product.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return "Hello World!";
    }


}
