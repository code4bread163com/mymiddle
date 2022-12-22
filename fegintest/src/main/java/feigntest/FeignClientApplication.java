package feigntest;

import fegininterface.SampleInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication(scanBasePackages ="feigntest")
//@EnableCircuitBreaker
@EnableFeignClients(clients = SampleInterface.class)
@EnableDiscoveryClient
public class FeignClientApplication {
    public static void main(String[] args) {

        SpringApplication.run(FeignClientApplication.class, args);
    }
}
