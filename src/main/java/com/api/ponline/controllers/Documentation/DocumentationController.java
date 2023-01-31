package com.api.ponline.controllers.Documentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/documentation")
public class DocumentationController {
    
    @GetMapping()
    public RedirectView home() {
        return new RedirectView("/documentation/index.html");
    }
    
}
