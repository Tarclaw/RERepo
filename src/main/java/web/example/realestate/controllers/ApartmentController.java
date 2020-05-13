package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.ApartmentCommand;
import web.example.realestate.services.ApartmentService;

@Controller
public class ApartmentController {

    private final ApartmentService service;

    public ApartmentController(ApartmentService service) {
        this.service = service;
    }

    @GetMapping("/apartment/{id}/show")
    public String getApartmentById(@PathVariable String id, Model model) {
        model.addAttribute("apartment", service.getById(Long.valueOf(id)));
        return "apartment/show";
    }

    @GetMapping("/apartment")
    public String getAllApartments(Model model) {
        model.addAttribute("apartments", service.getApartments());
        return "apartments";
    }

    @GetMapping("/apartment/new")
    public String newApartment(Model model) {
        model.addAttribute("apartment", new ApartmentCommand());
        return "apartment/apartmentForm";
    }

    @GetMapping("/apartment/{id}/update")
    public String updateApartment(@PathVariable String id, Model model) {
        model.addAttribute("apartment", service.findCommandById(Long.valueOf(id)));
        return "apartment/apartmentForm";
    }

    @PostMapping("/apartment")
    public String saveOrUpdate(@ModelAttribute ApartmentCommand command) {
        ApartmentCommand savedCommand = service.saveApartmentCommand(command);
        return "redirect:/apartment/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/apartment/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
