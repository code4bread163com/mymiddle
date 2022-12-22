package zookeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import zookeeper.ClientService;

/**
 * Created by apple on 2018/5/29.
 */
@Controller
@EnableAutoConfiguration
public class SampleController {
    @Autowired
    private ClientService clientService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        clientService.testNodeCreate();
        return "Hello World!";
    }
}
