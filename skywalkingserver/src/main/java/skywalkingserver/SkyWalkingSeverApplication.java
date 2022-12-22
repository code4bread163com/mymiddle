package skywalkingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SkyWalkingSeverApplication {
    public static void main(String[] args) {


        SpringApplication.run(SkyWalkingSeverApplication.class, args);


    }
}
