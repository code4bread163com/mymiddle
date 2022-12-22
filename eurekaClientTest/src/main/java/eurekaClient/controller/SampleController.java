package eurekaClient.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by appe on 2018/5/29.
 */
@RestController
@EnableAutoConfiguration
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    @Autowired
    private DiscoveryClient client;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductService1 productService1;

    @RequestMapping(value = "getConsumer")
    public String getConsumer(){
        String str =  productService.getProduct();
        String str1 =  productService1.getProduct1();
        return str;
    }

    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/getConsumer1")
    @ResponseBody
    public String getConsumer1(){
        /**
         * 地址是http://service-provider,不是http://127.0.0.1:8082/
         * 因为向注册中心注册了服务，服务名称service-provider,访问service-provider即可
         */
        String data = restTemplate.getForObject("http://eureka-product/getProduct", String.class);
        return data;
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {

        try {
            List<String> services = client.getServices();
//            ServiceInstance instance = client.getInstances();
//            logger.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return "Hello World!";
    }
}
