package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.BasementService;
import web.example.realestate.services.ClientService;

@Controller
public class BasementController {

    private final BasementService basementService;
    private final ClientService clientService;

    public BasementController(BasementService basementService, ClientService clientService) {
        this.basementService = basementService;
        this.clientService = clientService;
    }

    @GetMapping("/basement/{id}/show")
    public String getBasementById(@PathVariable String id, Model model) {
        model.addAttribute("basement", basementService.getById(Long.valueOf(id)));
        return "basement/show";
    }

    @GetMapping("/basement")
    public String getAllBasements(Model model) {
        model.addAttribute("basements", basementService.getBasements());
        return "basements";
    }

    @GetMapping("/basement/new")
    public String newBasement(Model model) {
        model.addAttribute("basement", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "basement/basementEmptyForm";
    }

    @GetMapping("/basement/{id}/update")
    public String updateBasement(@PathVariable String id, Model model) {
        model.addAttribute("basement", basementService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "basement/basementForm";
    }

    @PostMapping("/basement/save")
    public String saveNew(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = basementService.saveDetached(command);
        return "redirect:/basement/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/basement/update")
    public String updateExisting(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = basementService.saveAttached(command);
        return "redirect:/basement/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/basement/{id}/delete")
    public String deleteById(@PathVariable String id) {
        basementService.deleteById(Long.valueOf(id));
        return "redirect:/basements";
    }
}
