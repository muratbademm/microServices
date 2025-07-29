package com.murat.lessonService.config;

import com.murat.lessonService.client.StudentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    //Proxy servisler arası doğrudan REST çağrısını basit bir method çağrısı gibi kullanmamız olanak sağlar.
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    //LoadBalancedExchangeFilterFunction sayesinde,
    // studentService adı Eureka’dan IP ve port bilgisi ile çözülüyor (dinamik olarak).
    @Bean
    public WebClient studentWebClient(){
        return WebClient.builder().baseUrl("http://studentService").filter(filterFunction).build();
        //Bu WebClient, base URL olarak "http://studentService" adresini kullanıyor.
    }
    @Bean
    public StudentClient studentClient(){
        HttpServiceProxyFactory httpServiceProxyFactory= HttpServiceProxyFactory.builderFor(WebClientAdapter.create(studentWebClient())).build();
        return  httpServiceProxyFactory.createClient(StudentClient.class);
        //HttpServiceProxyFactory ile StudentClient interface’inin proxy nesnesi oluşturuluyor.
    }
}
