package fegininterface;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://127.0.0.1:12345", value = "sampleInterface", path = "/api")
public interface SampleInterface {

    //    @RequestMapping(value = "/instance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/instance1")
    String home1();

    @GetMapping(value = "/instance2")
    String home2();

}
