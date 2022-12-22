package activiti.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by apple on 2018/5/29.
 */
@Controller
@EnableAutoConfiguration
public class SampleController {


    @RequestMapping("/")
    @ResponseBody
    String home() {


        return "Hello World!";
    }


}
