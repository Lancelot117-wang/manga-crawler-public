package com.wjh.manga_crawler;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.spring.SpringGeccoEngine;
import com.geccocrawler.gecco.spring.SpringPipelineFactory;
import com.wjh.manga_crawler.utils.s3connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class MangaCrawlerApplication {

    @Bean
    public SpringPipelineFactory springPipelineFactory() {
        return new SpringPipelineFactory();
    }

    @Bean
    public SpringGeccoEngine initGecco() {
        return new SpringGeccoEngine() {
            @Override
            public void init() {
                GeccoEngine.create()
                        .pipelineFactory(springPipelineFactory())
                        //project path
                        .classpath("com.wjh")
                        //crawler starting endpoints
                        .seed(new HttpGetRequest("https://manganelo.com/genre-all/1"))
                        //set number of threads
                        .thread(1)
                        //interval between adjacent requests
                        .interval(10)
                        //use PC user-agent
                        .mobile(false)
                        //execute
                        .start();
            }
        };
    }

    public static void main(String[] args) throws Exception {
        s3connector.init_with_key();
        SpringApplication.run(MangaCrawlerApplication.class, args);
    }
}