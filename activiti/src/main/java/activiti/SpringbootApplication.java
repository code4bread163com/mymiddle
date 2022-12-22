package activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        try {
            Object a = null;
            System.out.println(String.valueOf(a));
            System.out.println(a.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        SpringApplication.run(SpringbootApplication.class, args);


    }

}
