package ar.unrn.tp.controller;

import ar.unrn.tp.api.PostService;
import ar.unrn.tp.controller.request.AuthorCount;
import ar.unrn.tp.domain.Post;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<Post> find(@RequestParam String id) {
        return postService.findPost(id);
    }

    @GetMapping("/latest")
    public List<Post> findLatest() {
        return postService.findLatestPosts();
    }

    @GetMapping("/byauthor")
    public List<AuthorCount> countByAuthor() {
        return postService.countPostsByAuthor();
    }

    @GetMapping("/author/{name}")
    public List<Post> findByAuthor(@PathVariable String name) {
        return postService.findPostsByAuthor(name);
    }

    @GetMapping("/search/{text}")
    public List<Post> findByText(@PathVariable String text) {
        return postService.findPostsByText(text);
    }

}