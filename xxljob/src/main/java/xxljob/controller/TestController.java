package xxljob.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * Created by apple on 2018/5/29.
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/helloworld")
    public String helloworld() {
        log.info(">>>>>>>>>>> xxl-job config init.");

        return "helloworld";
    }



}
