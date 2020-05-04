package ru.agiletech.composite.sprint.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableFeignClients(basePackages = "ru.agiletech.composite.sprint.service.client")
public class FeignClientConfig {

    private final ApplicationContext appContext;
    private final ObjectMapper objectMapper;

    private final int readTimeOut;
    private final int connectionTimeOut;

    @Autowired
    public FeignClientConfig(ApplicationContext                     appContext,
                             ObjectMapper                           objectMapper,
                             @Value("${rest.readTimeOut}")          int readTimeOut,
                             @Value("${rest.connectionTimeOut}")    int connectionTimeOut) {

        this.appContext = appContext;
        this.objectMapper = objectMapper;
        this.readTimeOut = readTimeOut;
        this.connectionTimeOut = connectionTimeOut;
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.NONE;
    }

    @Bean
    public FeignClientBuilder feignClientBuilder(){
        return new FeignClientBuilder(appContext);
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(connectionTimeOut, readTimeOut);
    }

    @Bean
    public Decoder feignDecoder() {
        List<HttpMessageConverter<?>> additionalConverters = new ArrayList<>();

        additionalConverters.add(new MappingJackson2HttpMessageConverter());
        additionalConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        additionalConverters.add(new ByteArrayHttpMessageConverter());
        additionalConverters.add(new FormHttpMessageConverter());

        return new ResponseEntityDecoder(
                new SpringDecoder(() -> new HttpMessageConverters(additionalConverters)));
    }

    @Bean
    public Encoder feignEncoder() {
        HttpMessageConverter<?> jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

}
