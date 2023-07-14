package com.olacodes.taskservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.modelmapper.ModelMapper

@Configuration
class ModelMapperConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}