package com.uniclans.ophthalmology.basecomponent.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import groovy.util.logging.Slf4j;

@Slf4j
@Configuration
public class FreeMarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;


    @Value("${imgRes}")
    private String imgRes;



    @PostConstruct
    public void setConfigure() throws Exception {

        configuration.setSharedVariable("imgRes", imgRes);

    }

}