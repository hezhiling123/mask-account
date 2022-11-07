package cn.hezhiling.test.hello.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 13:57:36
 */
@RestController
public class HelloController {
    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
