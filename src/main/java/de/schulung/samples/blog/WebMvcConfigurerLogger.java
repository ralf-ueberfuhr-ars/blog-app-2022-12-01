package de.schulung.samples.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Slf4j
@Profile("dev")
public class WebMvcConfigurerLogger {

    @EventListener(ContextRefreshedEvent.class)
    public void logWebMvcConfigurersOnEvent(ContextRefreshedEvent evt) {
        evt.getApplicationContext().getBeansOfType(WebMvcConfigurer.class)
          .values()
          .stream()
          .map(Object::toString)
          .forEach(log::info);
    }

}
