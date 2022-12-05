package de.schulung.samples.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfiguration {

    @Bean
    WebMvcConfigurer configureWeb() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addRedirectViewController("/","/index.html");
            }
        };
    }

    @EventListener(ContextRefreshedEvent.class)
    public void logWebMvcConfigurersOnEvent(ContextRefreshedEvent evt) {
        evt.getApplicationContext().getBeansOfType(WebMvcConfigurer.class)
          .values()
          .stream()
          .map(Object::toString)
          .forEach(log::info);
    }

}
