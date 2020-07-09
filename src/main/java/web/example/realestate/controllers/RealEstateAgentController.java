package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.RealEstateAgentService;

import javax.validation.Valid;

@Controller
public class RealEstateAgentController {

    private final RealEstateAgentService agentService;
    private final ClientService clientService;

    public RealEstateAgentController(RealEstateAgentService agentService, ClientService clientService) {
        this.agentService = agentService;
        this.clientService = clientService;
    }

    @GetMapping("/realEstateAgent/{id}/show")
    public String getRealEstateAgentById(@PathVariable String id, Model model) {
        model.addAttribute("realEstateAgent", agentService.getById(Long.valueOf(id)));
        return "realEstateAgent/show";
    }

    @GetMapping("/realEstateAgents")
    public String getAllRealEstateAgents(Model model) {
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "realEstateAgent";
    }

    @GetMapping("/realEstateAgent/new")
    public String newAgent(Model model) {
        model.addAttribute("realEstateAgent", new RealEstateAgentCommand());
        model.addAttribute("clients", clientService.getClients());
        return "realEstateAgent/realEstateAgentForm";
    }

    @GetMapping("/realEstateAgent/{id}/update")
    public String updateAgent(@PathVariable String id, Model model) {
        model.addAttribute("realEstateAgent", agentService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "realEstateAgent/realEstateAgentForm";
    }

    @PostMapping("/realEstateAgent")
    public String saveOrUpdate(@Valid @ModelAttribute("realEstateAgent") RealEstateAgentCommand command,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            model.addAttribute("realEstateAgent", command);
            model.addAttribute("clients", clientService.getClients());
            return "realEstateAgent/realEstateAgentForm";
        }
        RealEstateAgentCommand savedCommand = agentService.saveRealEstateAgentCommand(command);
        return "redirect:/realEstateAgent/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/realEstateAgent/{id}/delete")
    public String deleteById(@PathVariable String id) {
        agentService.deleteById(Long.valueOf(id));
        return "redirect:/realEstateAgents";
    }
}
