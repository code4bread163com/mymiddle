package xxljob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication(scanBasePackages = {"xxljob.controller"})

public class SpringbootApplication {
    public static void main(String[] args) {


        SpringApplication.run(SpringbootApplication.class, args);


    }
}
