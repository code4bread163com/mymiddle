package feigntest.controller;

import fegininterface.SampleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by apple on 2018/5/29.
 */
@RestController
@RequestMapping(value = "/test")
public class SampleController {
    @Autowired
    private SampleInterface sampleInterface;

    @GetMapping(value = "/home")
    public String home() {
        try {
            String test = sampleInterface.home1();
            System.out.println(test);
            return test;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "error";
    }
}
