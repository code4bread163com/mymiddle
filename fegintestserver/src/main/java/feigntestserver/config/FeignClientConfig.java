//package feigntestserver.config;
//
//import feign.Contract;
//import feign.Logger;
//import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignClientConfig {
//    @Bean
//    public Contract feignContract() {
//        return new SpringMvcContract();
//    }
//
//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
//
//}
