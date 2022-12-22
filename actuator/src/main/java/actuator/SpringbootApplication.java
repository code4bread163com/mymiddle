package actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by apple on 2018/5/29.
 */
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {


        SpringApplication.run(SpringbootApplication.class, args);


    }

//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName){
//        return registry -> registry.config().commonTags("application",applicationName);
//    }
}
