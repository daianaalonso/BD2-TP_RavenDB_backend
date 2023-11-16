package ar.unrn.tp.controller;

import ar.unrn.tp.api.PageService;
import ar.unrn.tp.domain.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/pages")
@AllArgsConstructor
public class PageController {
    private final PageService pageService;

    @PostMapping
    public Page insert(@RequestBody Page page) {
        return pageService.insertPage(page);
    }

    @GetMapping
    public List<Page> find(@RequestParam String id) {
        return pageService.findPage(id);
    }
}