package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ClientService;

@Controller
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/client/{id}/show")
    public String getClientById(@PathVariable String id, Model model) {
        model.addAttribute("client", service.getById(Long.valueOf(id)));
        return "client/show";
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", service.getClients());
        return "client";
    }

    @GetMapping("/client/new")
    public String newClient(Model model) {
        model.addAttribute("client", new ClientCommand());
        return "client/clientForm";
    }

    @GetMapping("/apartment/client/new")
    public String newClientForApartment(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", "apartment");
        return "client/clientEmptyForm";
    }

    @GetMapping("/client/{id}/update")
    public String updateClient(@PathVariable String id, Model model) {
        model.addAttribute("client", service.findCommandById(Long.valueOf(id)));
        return "client/clientForm";
    }

    @PostMapping("/client")
    public String saveOrUpdate(@ModelAttribute ClientCommand command) {
        ClientCommand savedCommand = service.saveClientCommand(command);
        return "redirect:/client/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/client/apartment")
    public String saveForApartment(@ModelAttribute ClientCommand command, Model model) {
        service.saveClientCommand(command);
        model.addAttribute("apartment", new FacilityCommand());
        model.addAttribute("clients", service.getClients());
        return "apartment/apartmentEmptyForm";
    }

    @GetMapping("/client/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/clients";
    }
}
