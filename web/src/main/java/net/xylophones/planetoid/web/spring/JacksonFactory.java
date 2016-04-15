package net.xylophones.planetoid.web.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonFactory {

    @Bean
    public ObjectMapper jackson() {
        return new ObjectMapper();
    }

}
