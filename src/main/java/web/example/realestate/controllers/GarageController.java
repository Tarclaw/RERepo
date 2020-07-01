package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.GarageService;

@Controller
public class GarageController {

    private final GarageService garageService;
    private final ClientService clientService;

    public GarageController(GarageService garageService, ClientService clientService) {
        this.garageService = garageService;
        this.clientService = clientService;
    }

    @GetMapping("/garage/{id}/show")
    public String getGarageById(@PathVariable String id, Model model) {
        model.addAttribute("garage", garageService.getById(Long.valueOf(id)));
        return "garage/show";
    }

    @GetMapping("/garage")
    public String getAllGarages(Model model) {
        model.addAttribute("garages", garageService.getGarages());
        return "garages";
    }

    @GetMapping("/garage/new")
    public String newGarage(Model model) {
        model.addAttribute("garage", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "garage/garageEmptyForm";
    }

    @GetMapping("/garage/{id}/update")
    public String updateGarage(@PathVariable String id, Model model) {
        model.addAttribute("garage", garageService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "garage/garageForm";
    }

    @PostMapping("/garage/save")
    public String saveNew(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = garageService.saveDetached(command);
        return "redirect:/garage/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/garage/update")
    public String updateExisting(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = garageService.saveAttached(command);
        return "redirect:/garage/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/garage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        garageService.deleteById(Long.valueOf(id));
        return "redirect:/garage";
    }
}
