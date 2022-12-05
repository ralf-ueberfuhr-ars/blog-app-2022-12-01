package de.schulung.samples.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping(BlogPostController.CONTROLLER_MAPPING)
@RequiredArgsConstructor
public class BlogPostController {

    public static final String CONTROLLER_MAPPING = "/posts";
    public static final String VIEW_POSTS_MAPPING = "/view.html";

    private final BlogPostService service;

    @GetMapping(BlogPostController.VIEW_POSTS_MAPPING)
    public String viewBlogPosts(Model model) {
        // BlogPosts ermitteln
        Collection<BlogPost> posts = service.findPosts();
        // Weitergeben der Posts an die JSP
        model.addAttribute("posts", posts);
        return "view-posts";
    }

    @GetMapping("/findall")
    @ResponseBody
    public Collection<BlogPost> getBlogPosts() {
        return service.findPosts();
    }

    @GetMapping("/find")
    @ResponseBody
    public BlogPost getBlogPost(@RequestParam("id") long id) {
        return service.findPostById(id).orElse(null);
    }

    @PostMapping("/create")
    public String createBlogPost(
      @RequestParam("title")
      String title,
      @RequestParam("content")
      String content) {

        BlogPost post = BlogPost.builder()
          .title(title)
          .content(content)
          .build();
        service.addPost(post);
        return "redirect:"
          + BlogPostController.CONTROLLER_MAPPING
          + BlogPostController.VIEW_POSTS_MAPPING;

    }

}
