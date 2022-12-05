package de.schulung.samples.blog.boundary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * We need the separate class instead of registering via WebMvcConfigurer,
 * because the ControllerAdvice does only work for this solution.
 */
@Controller
public class WelcomeController {

    @GetMapping("/index.html")
    public String welcome() {
        return "index";
    }

}
