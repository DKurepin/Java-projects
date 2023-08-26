package com.kurepin.lab4;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Lab4Configuration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}