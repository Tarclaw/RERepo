package web.example.realestate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RealEstateAgentController.class);

    public RealEstateAgentController(RealEstateAgentService agentService, ClientService clientService) {
        this.agentService = agentService;
        this.clientService = clientService;
        LOGGER.info("New instance of RealEstateAgentController created.");
    }

    @GetMapping("/realEstateAgent/{id}/show")
    public String getRealEstateAgentById(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'getRealEstateAgentById(@PathVariable String id, Model model)' method");

        model.addAttribute("realEstateAgent", agentService.getById(Long.valueOf(id)));
        LOGGER.debug("add realEstateAgent attribute to model");

        LOGGER.trace("'getRealEstateAgentById(@PathVariable String id, Model model)' executed successfully.");
        return "realEstateAgent/show";
    }

    @GetMapping("/realEstateAgents")
    public String getAllRealEstateAgents(Model model) {
        LOGGER.trace("Enter in 'getAllRealEstateAgents(Model model)' method");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'getAllRealEstateAgents(Model model)' executed successfully.");
        return "realEstateAgent";
    }

    @GetMapping("/realEstateAgent/new")
    public String newAgent(Model model) {
        LOGGER.trace("Enter in 'newAgent(Model model)' method");

        model.addAttribute("realEstateAgent", new RealEstateAgentCommand());
        LOGGER.debug("add realEstateAgents attribute to model");

        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'newAgent(Model model)' executed successfully.");
        return "realEstateAgent/realEstateAgentForm";
    }

    @GetMapping("/realEstateAgent/{id}/update")
    public String updateAgent(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'updateAgent(@PathVariable String id, Model model)' method");

        model.addAttribute("realEstateAgent", agentService.findCommandById(Long.valueOf(id)));
        LOGGER.debug("add realEstateAgents attribute to model");
        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'updateAgent(@PathVariable String id, Model model)' executed successfully.");
        return "realEstateAgent/realEstateAgentForm";
    }

    @PostMapping("/realEstateAgent")
    public String saveOrUpdate(@Valid @ModelAttribute("realEstateAgent") RealEstateAgentCommand command,
                               BindingResult bindingResult, Model model) {
        LOGGER.trace("Enter in 'saveOrUpdate(RealEstateAgentCommand command, BindingResult bindingResult, Model model)' method");

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("realEstateAgent", command);
            LOGGER.debug("add realEstateAgents attribute to model");

            model.addAttribute("clients", clientService.getClients());
            LOGGER.debug("add clients attribute to model");

            return "realEstateAgent/realEstateAgentForm";
        }
        RealEstateAgentCommand savedCommand = agentService.saveRealEstateAgentCommand(command);

        LOGGER.trace("'saveOrUpdate(RealEstateAgentCommand command, BindingResult bindingResult, Model model)' executed successfully.");
        return "redirect:/realEstateAgent/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/realEstateAgent/{id}/delete")
    public String deleteById(@PathVariable String id) {
        LOGGER.trace("Enter in 'deleteById(@PathVariable String id)' method");

        agentService.deleteById(Long.valueOf(id));

        LOGGER.trace("'deleteById(@PathVariable String id)' executed successfully.");
        return "redirect:/realEstateAgents";
    }
}
