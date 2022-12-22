package springShutDown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);

        try {
            //DbService4ApplApptImpl task = context.getBean(DbService4ApplApptImpl.class);

            for (int i = 0; i < 10000; i++) {
                log.info("testtest" + i);
//                task.doTaskOne();
//                task.doTaskTwo();
//                task.doTaskThree();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
