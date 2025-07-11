package edu.fdzc.dorm_text.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                //.allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
