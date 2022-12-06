package de.schulung.samples.blog.boundary;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    // TODO use boundary model
    public Collection<BlogPost> getBlogPosts() {
        return service.findPosts();
    }

    @GetMapping("/find")
    @ResponseBody
    // TODO use boundary model
    public BlogPost getBlogPost(@RequestParam("id") long id) {
        return service.findPostById(id).orElse(null);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('AUTHOR')") // TODO create custom expression to check SecurityRole
    public String createBlogPost(
      @RequestParam("title")
      String title,
      @RequestParam("content")
      String content,
      Authentication authentication
    ) {
        if(title.length()<3) {
            return "redirect:/index.html?error=title";
        } else {
            BlogPost post = BlogPost.builder()
              .title(title)
              .content(content)
              .author(authentication.getName())
              .build();
            service.addPost(post);
            return "redirect:"
              + BlogPostController.CONTROLLER_MAPPING
              + BlogPostController.VIEW_POSTS_MAPPING;
        }

    }

}
