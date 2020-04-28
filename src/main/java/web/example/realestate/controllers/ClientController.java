package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.ClientService;

@Controller
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @RequestMapping("/clients/show/{id}")
    public String getClientById(@PathVariable String id, Model model) {
        model.addAttribute("client", service.getById(Long.valueOf(id)));
        return "clients/show";
    }

    @RequestMapping("/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", service.getClients());
        return "clients";
    }
}
