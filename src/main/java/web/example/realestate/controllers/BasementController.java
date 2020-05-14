package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.BasementService;

@Controller
public class BasementController {

    private final BasementService service;

    public BasementController(BasementService service) {
        this.service = service;
    }

    @GetMapping("/basements/{id}/show")
    public String getBasementById(@PathVariable String id, Model model) {
        model.addAttribute("basement", service.getById(Long.valueOf(id)));
        return "basements/show";
    }

    @GetMapping("/basements")
    public String getAllBasements(Model model) {
        model.addAttribute("basements", service.getBasements());
        return "basement";
    }

    @GetMapping("/basement/new")
    public String newBasement(Model model) {
        model.addAttribute("basement", new FacilityCommand());
        return "basement/basementForm";
    }

    @GetMapping("/basement/{id}/update")
    public String updateBasement(@PathVariable String id, Model model) {
        model.addAttribute("basement", service.findCommandById(Long.valueOf(id)));
        return "basement/basementForm";
    }

    @PostMapping("/basement")
    public String saveOrUpdate(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = service.saveBasementCommand(command);
        return "redirect:/basement/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/basement/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
