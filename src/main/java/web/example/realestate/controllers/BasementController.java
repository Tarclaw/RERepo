package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.BasementService;

@Controller
public class BasementController {

    private final BasementService service;

    public BasementController(BasementService service) {
        this.service = service;
    }

    @RequestMapping("/besements/show/{id}")
    public String getBasementById(@PathVariable String id, Model model) {
        model.addAttribute("basement", service.getById(Long.valueOf(id)));
        return "basements/show";
    }

    @RequestMapping("/besements")
    public String getAllBasements(Model model) {
        model.addAttribute("basements", service.getBasements());
        return "basements";
    }
}
