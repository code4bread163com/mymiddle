package zuul.limit;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zuul.limit.service.TestService;
import zuul.limit.service.TestServiceImpl;

/**
 * Created by apple on 2018/5/29.
 */
@RestController
public class SampleController {

    @Autowired
    private TestServiceImpl service;

    @GetMapping("/temp")
    public String temp(@RequestParam(required = false) Long t) throws Exception {
        System.out.println(t);
        service.temp();
        return String.valueOf(System.currentTimeMillis());
    }

    @GetMapping("/test1")
    public String test1(@RequestParam(required = false) Long t) throws Exception {
        System.out.println(t);
        try {
            service.test1();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.valueOf(System.currentTimeMillis());
    }

    @GetMapping("/test2")
    public String test2(@RequestParam(required = false) Long t) throws Exception {
        System.out.println(t);
        return service.test2(t);
    }

    @GetMapping("/test3/{name}")
    public String test3(@PathVariable("name") String name) {
        return service.test3(name);
    }

}
