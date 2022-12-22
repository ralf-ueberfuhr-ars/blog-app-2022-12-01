package de.schulung.samples.blog.boundary;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.shared.config.SecurityRoles.OnlyAuthors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Controller
@RequestMapping(BlogPostController.CONTROLLER_MAPPING)
@RequiredArgsConstructor
public class BlogPostController {

    public static final String CONTROLLER_MAPPING = "/posts";
    public static final String VIEW_POSTS_MAPPING = "/view.html";

    private final BlogPostService service;

    @GetMapping(BlogPostController.VIEW_POSTS_MAPPING)
    public String viewBlogPosts(Model model, @RequestParam(value = "tag", required = false) String tag) {
        // BlogPosts ermitteln
        Collection<BlogPost> posts = null == tag || tag.isEmpty() ? service.findPosts() : service.findPostsByTag(tag);
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
    @OnlyAuthors
    public String createBlogPost(
      @RequestParam("title")
      String title,
      @RequestParam("content")
      String content,
      @RequestParam(value = "hashTags", required = false)
      String hashTags,
      Authentication authentication
    ) {
        if (title.length() < 3) {
            return "redirect:/index.html?error=title";
        } else {
            BlogPost post = BlogPost.builder()
              .title(title)
              .content(content)
              .author(authentication.getName())
              .build();
            if (null != hashTags) {
                post.setHashTags(
                  Arrays.stream(hashTags.split(","))
                    .filter(not(String::isBlank))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .map(name -> HashTag.builder().name(name).build())
                    .collect(Collectors.toList())
                );
            }
            service.addPost(post);
            return "redirect:"
              + BlogPostController.CONTROLLER_MAPPING
              + BlogPostController.VIEW_POSTS_MAPPING;
        }

    }

}
