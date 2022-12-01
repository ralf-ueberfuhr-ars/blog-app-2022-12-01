package de.schulung.samples.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class BlogPostController {

    @GetMapping("/findall")
    @ResponseBody
    public Collection<BlogPost> getBlogPosts() {

        return List.of(
          BlogPost.builder()
            .id(1L)
            .title("Mein erster Post")
            .content("Lorem ipsum...")
            .timestamp(LocalDateTime.now())
            .build(),
          BlogPost.builder()
            .id(1L)
            .title("Mein zweiter Post")
            .content("Lorem ipsum...")
            .timestamp(LocalDateTime.now())
            .build(),
          BlogPost.builder()
            .id(1L)
            .title("Mein dritter Post")
            .content("Lorem ipsum...")
            .timestamp(LocalDateTime.now())
            .build()
        );

    }


}
