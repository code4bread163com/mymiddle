package actuator.controller;

import actuator.util.EnumUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 2018/5/29.
 */
@Controller
@EnableAutoConfiguration
public class SampleController {


    @Autowired
    private MeterRegistry meterRegistry;

    @RequestMapping("/")
    @ResponseBody
    String home() {

        try {
            Timer timer = Timer.builder("test-1")
                    .description("just test")
                    .tag("url", "http://")
                    .register(meterRegistry);

            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
            }

            timer.record(Duration.ofMillis(System.currentTimeMillis()));


            System.out.println(timer.count());
            System.out.println(timer.measure());
            System.out.println(timer.totalTime(TimeUnit.SECONDS));
            System.out.println(timer.mean(TimeUnit.SECONDS));
            System.out.println(timer.max(TimeUnit.SECONDS));


            Timer.Sample sample = Timer.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Timer timer1 = meterRegistry.timer("test-2");
            sample.stop(timer1);

            System.out.println(timer1.count());
            System.out.println(timer1.measure());
            System.out.println(timer1.totalTime(TimeUnit.SECONDS));
            System.out.println(timer1.mean(TimeUnit.SECONDS));
            System.out.println(timer1.max(TimeUnit.SECONDS));


//            new Thread(() -> {
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                sample.stop(meterRegistry.timer("test-2"));
////            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        EnumUtil.getValue("");

        return "Hello World!";
    }


}
