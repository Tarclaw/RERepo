package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.MappingCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.RealEstateAgentService;

@Controller
public class ClientController {

    private final ClientService clientService;
    private final RealEstateAgentService agentService;

    public ClientController(ClientService clientService, RealEstateAgentService agentService) {
        this.clientService = clientService;
        this.agentService = agentService;
    }

    @GetMapping("/client/{id}/show")
    public String getClientById(@PathVariable String id, Model model) {
        model.addAttribute("client", clientService.getById(Long.valueOf(id)));
        return "client/show";
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "client";
    }

    @GetMapping("/client/new")
    public String newClient(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("facility", new FacilityCommand());
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientEmptyForm";
    }

    @GetMapping("/client/{id}/update")
    public String updateClient(@PathVariable String id, Model model) {
        model.addAttribute("client", clientService.findCommandById(Long.valueOf(id)));
        return "client/clientForm";
    }

    @PostMapping("/client")
    public String saveOrUpdate(@ModelAttribute ClientCommand command) {
        ClientCommand savedCommand = clientService.saveAttached(command);
        return "redirect:/client/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/client/save")
    public String save(@ModelAttribute ClientCommand clientCommand, @ModelAttribute FacilityCommand facilityCommand) {
        clientService.saveDetached(clientCommand, facilityCommand);
        return "redirect:/clients";
    }

    @GetMapping("/client/{id}/delete")
    public String deleteById(@PathVariable String id) {
        clientService.deleteById(Long.valueOf(id));
        return "redirect:/clients";
    }

    @GetMapping("/apartment/client/new")
    public String newClientForApartment(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("apartment"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/basement/client/new")
    public String newClientForBasement(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("basement"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/garage/client/new")
    public String newClientForGarage(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("garage"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/house/client/new")
    public String newClientForHouse(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("house"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/storage/client/new")
    public String newClientForStorage(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("storage"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @PostMapping("/client/facility/save")
    public String saveForFacility(@ModelAttribute ClientCommand clientCommand,
                                  @ModelAttribute MappingCommand mappingCommand) {
        clientService.saveAttached(clientCommand);
        return "redirect:/" + mappingCommand.getPageName() + "/new";
    }
}
