package actuator.util;


import actuator.controller.SampleController;
import actuator.controller.ServiceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangjian
 * @create 2019-03-27
 */
@Slf4j
public class EnumUtil {

    private static SampleController SampleController = ServiceLocator.getBean(SampleController.class);
    private static String name = ServiceLocator.getApplicationContext().getApplicationName();
    private static String name1 = ServiceLocator.getApplicationContext().getEnvironment().getProperty("spring.application.name");





    public static String getValue(String name) {

        return name;
    }

}
