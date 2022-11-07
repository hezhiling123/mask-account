package cn.hezhiling.test.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-26 17:19:07
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
