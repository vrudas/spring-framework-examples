package io.sfe.notesapp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final String applicationName;

    public IndexController(@Value("${spring.application.name}") String applicationName) {
        this.applicationName = applicationName;
    }

    @GetMapping({"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("applicationName", applicationName);
        return "index";
    }
}
