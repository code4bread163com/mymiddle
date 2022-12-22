package feigntestserver.controller;

import fegininterface.SampleInterface;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@EnableAutoConfiguration
public class ServerController  {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {

        System.out.println("Hello World test!");
        return "Hello World test!";
    }

    @RequestMapping("/testSleep")
    @ResponseBody
    public String testNoSleep() {

        System.out.println("Hello World testNoSleep!");

        return "Hello World testNoSleep!";
    }

    @RequestMapping("/ex1")
    @ResponseBody
    public String testException() {
        throw new RuntimeException("发生了异常");


    }
}
