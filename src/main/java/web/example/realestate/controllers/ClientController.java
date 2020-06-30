package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
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

    @GetMapping("/apartment/client/new")
    public String newClientForApartment(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", "apartment");
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
        facilityCommand.setItApartment(true); //todo temp
        clientService.saveDetached(clientCommand, facilityCommand);
        return "redirect:/clients";
    }

    @PostMapping("/client/apartment")
    public String saveForApartment(@ModelAttribute ClientCommand command, Model model) {
        clientService.saveAttached(command);
        model.addAttribute("apartment", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentEmptyForm";
    }

    @GetMapping("/client/{id}/delete")
    public String deleteById(@PathVariable String id) {
        clientService.deleteById(Long.valueOf(id));
        return "redirect:/clients";
    }
}
