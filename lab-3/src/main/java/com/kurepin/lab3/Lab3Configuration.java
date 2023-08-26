package com.kurepin.lab3;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Lab3Configuration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
