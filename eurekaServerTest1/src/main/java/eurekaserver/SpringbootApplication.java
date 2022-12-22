package eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringbootApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);



    }
}
