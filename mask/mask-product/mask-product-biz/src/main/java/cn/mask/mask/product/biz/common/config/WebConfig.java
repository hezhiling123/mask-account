package cn.mask.mask.product.biz.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.DateFormat;

@Configuration
public class WebConfig {

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Bean
    public MappingJackson2HttpMessageConverter MappingJsonpHttpMessageConverter() {

        // ObjectMapper为了保障线程安全性，里面的配置类都是一个不可变的对象
        ObjectMapper mapper = jackson2ObjectMapperBuilder.build();
        // 所以这里的setDateFormat的内部原理其实是创建了一个新的配置类
        DateFormat dateFormat = mapper.getDateFormat();
        mapper.setDateFormat(new MyDateFormat(dateFormat));

        return new MappingJackson2HttpMessageConverter(mapper);
    }
}
