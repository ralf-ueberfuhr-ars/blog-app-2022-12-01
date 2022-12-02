package de.schulung.samples.blog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("blog-app")
@Getter
@Setter
public class BlogAppConfigurationProperties {

    private String title = "My Simple App";

}
