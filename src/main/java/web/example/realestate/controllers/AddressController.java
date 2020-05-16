package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.services.AddressService;

@Controller
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/address/{id}/show")
    public String getAddressById(@PathVariable String id, Model model) {
        model.addAttribute("address", service.getById(Long.valueOf(id)));
        return "address/show";
    }

    @GetMapping("/addresses")
    public String getAddresses(Model model) {
        model.addAttribute("addresses", service.getAddresses());
        return "address";
    }

    @GetMapping("/address/new")
    public String newAddress(Model model) {
        model.addAttribute("address", new AddressCommand());
        return "address/addressForm";
    }

    @GetMapping("/address/{id}/update")
    public String updateAddress(@PathVariable String id, Model model) {
        model.addAttribute("address", service.findCommandById(Long.valueOf(id)));
        return "address/addressForm";
    }

    @PostMapping("/address")
    public String saveOrUpdate(@ModelAttribute AddressCommand command) {
        AddressCommand savedCommand = service.saveAddressCommand(command);
        return "redirect:/address/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/address/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
