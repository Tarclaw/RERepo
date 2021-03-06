package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.GarageCommand;
import web.example.realestate.services.GarageService;

@Controller
public class GarageController {

    private final GarageService service;

    public GarageController(GarageService service) {
        this.service = service;
    }

    @GetMapping("/garages/{id}/show")
    public String getGarageById(@PathVariable String id, Model model) {
        model.addAttribute("garage", service.getById(Long.valueOf(id)));
        return "garages/show";
    }

    @GetMapping("/garages")
    public String getAllGarages(Model model) {
        model.addAttribute("garages", service.getGarages());
        return "garage";
    }

    @GetMapping("/garage/new")
    public String newGarage(Model model) {
        model.addAttribute("garage", new GarageCommand());
        return "garage/garageForm";
    }

    @GetMapping("/garage/{id}/update")
    public String updateGarage(@PathVariable String id, Model model) {
        model.addAttribute("garage", service.findCommandById(Long.valueOf(id)));
        return "garage/garageForm";
    }

    @PostMapping("/garage")
    public String saveOrUpdate(@ModelAttribute GarageCommand command) {
        GarageCommand savedCommand = service.saveGarageCommand(command);
        return "redirect:/garage/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/garage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
