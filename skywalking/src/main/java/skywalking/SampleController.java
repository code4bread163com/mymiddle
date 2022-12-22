package skywalking;

import api.ProductBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by apple on 2018/5/29.
 */
@Controller
@EnableAutoConfiguration
@Slf4j
public class SampleController {

    @Autowired
    private ProductService productService;


    @RequestMapping("/")
    @ResponseBody
    String home() {
        log.info("Hello，traceId：{}", TraceContext.traceId());
        sleep1000();
        sleep2000();

        String str =  productService.getProduct();


        return "Hello World!";
    }

    // 需要追踪的方法
    @Trace
    private void sleep1000() {
        try {
            log.info("sleep1000，traceId：{}", TraceContext.traceId());



            Thread.sleep(1000);
        } catch (Exception ex) {

        }
    }

    private void sleep2000() {
        try {
            log.info("sleep2000，traceId：{}", TraceContext.traceId());

            Thread.sleep(21000);
        } catch (Exception ex) {

        }
    }


}
