package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ApartmentService;
import web.example.realestate.services.ClientService;

@Controller
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ClientService clientService;

    public ApartmentController(ApartmentService apartmentService, ClientService clientService) {
        this.apartmentService = apartmentService;
        this.clientService = clientService;
    }

    @GetMapping("/apartment/{id}/show")
    public String getApartmentById(@PathVariable String id, Model model) {
        model.addAttribute("apartment", apartmentService.getById(Long.valueOf(id)));
        return "apartment/show";
    }

    @GetMapping("/apartment")
    public String getAllApartments(Model model) {
        model.addAttribute("apartments", apartmentService.getApartments());
        return "apartments";
    }

    @GetMapping("/apartment/new")
    public String newApartment(Model model) {
        model.addAttribute("apartment", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentEmptyForm";
    }

    @GetMapping("/apartment/{id}/update")
    public String updateApartment(@PathVariable String id, Model model) {
        model.addAttribute("apartment", apartmentService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentForm";
    }

    @PostMapping("/apartment")
    public String saveOrUpdate(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = apartmentService.saveApartmentCommand(command);
        return "redirect:/apartment/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/apartment/{id}/delete")
    public String deleteById(@PathVariable String id) {
        apartmentService.deleteById(Long.valueOf(id));
        return "redirect:/apartment";
    }
}
