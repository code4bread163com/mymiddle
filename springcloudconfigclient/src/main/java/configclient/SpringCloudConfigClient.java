package configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
@RequestMapping("api")
@RefreshScope
public class SpringCloudConfigClient {
    public static void main(String[] args) {

        SpringApplication.run(SpringCloudConfigClient.class, args);
    }

    @Value("${hij}")
    private String username;

    /**
     * 访问首页
     */
    @GetMapping("/index")
    @ResponseBody
    public String index(){
        return "hello springboot！username：" + username;
    }


}
