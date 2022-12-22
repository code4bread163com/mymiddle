package feigntestserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class FeignServerApplication2 {
    public static void main(String[] args) {

        try {
            SpringApplication.run(FeignServerApplication2.class, args);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
