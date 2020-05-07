package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.services.RealEstateAgentService;

@Controller
public class RealEstateAgentController {

    private final RealEstateAgentService service;

    public RealEstateAgentController(RealEstateAgentService service) {
        this.service = service;
    }

    @GetMapping("/realEstateAgents/{id}/show")
    public String getRealEstateAgentById(@PathVariable String id, Model model) {
        model.addAttribute("realEstateAgent", service.getById(Long.valueOf(id)));
        return "realEstateAgents/show";
    }

    @GetMapping("/realEstateAgents")
    public String getAllRealEstateAgents(Model model) {
        model.addAttribute("realEstateAgents", service.getRealEstateAgents());
        return "realEstateAgent";
    }

    @GetMapping("/realEstateAgent/new")
    public String newAgent(Model model) {
        model.addAttribute("realEstateAgent", new RealEstateAgentCommand());
        return "realEstateAgent/realEstateAgentForm";
    }

    @GetMapping("/realEstateAgent/{id}/update")
    public String updateAgent(@PathVariable String id, Model model) {
        model.addAttribute("realEstateAgent", service.findCommandById(Long.valueOf(id)));
        return "realEstateAgent/realEstateAgentForm";
    }

    @PostMapping("/realEstateAgent")
    public String saveOrUpdate(@ModelAttribute RealEstateAgentCommand command) {
        RealEstateAgentCommand savedCommand = service.saveRealEstateAgentCommand(command);
        return "redirect:/realEstateAgent/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/realEstateAgent/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
