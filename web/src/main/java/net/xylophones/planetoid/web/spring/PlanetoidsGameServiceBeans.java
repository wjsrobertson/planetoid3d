package net.xylophones.planetoid.web.spring;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.PlanetoidsGameServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlanetoidsGameServiceBeans {

    @Bean
    public PlanetoidsGameService planetoidsGameService() {
        return new PlanetoidsGameServiceFactory().create();
    }

}
