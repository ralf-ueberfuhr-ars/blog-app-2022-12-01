package de.schulung.samples.blog.boundary;

import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.domain.HashTagService;
import de.schulung.samples.blog.shared.config.SecurityRoles.OnlyAuthors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashTagsController {

    private final HashTagService service;

    @GetMapping("/findall")
    @ResponseBody
    public Collection<HashTag> findAll() {
        return service.findAll();
    }

    @OnlyAuthors
    @PostMapping("/save")
    public String save(
      @RequestParam("name")
      String name,
      @RequestParam(value = "description", required = false)
      String description
    ) {
        var tag = HashTag.builder()
          .name(name)
          .description(description)
          .build();
        service.save(tag);
        return "redirect:/";
    }

}
