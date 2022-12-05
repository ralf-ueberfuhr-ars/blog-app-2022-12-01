package de.schulung.samples.blog.boundary.config;

import de.schulung.samples.blog.shared.config.BlogAppConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class BlogAppConfigurationPropertiesControllerAdvice {

    public static final String MODEL_NAME = "appConfig";

    @Getter(onMethod_ = @ModelAttribute(MODEL_NAME))
    private final BlogAppConfigurationProperties appConfig;

}
