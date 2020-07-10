package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.services.AddressService;

import javax.validation.Valid;

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

    @GetMapping("/address/{id}/update")
    public String updateAddress(@PathVariable String id, Model model) {
        model.addAttribute("address", service.findCommandById(Long.valueOf(id)));
        return "address/addressForm";
    }

    @PostMapping("/address")
    public String saveOrUpdate(@Valid @ModelAttribute("address") AddressCommand addressCommand,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            model.addAttribute("address", addressCommand);
            return "address/addressForm";
        }
        AddressCommand savedCommand = service.saveAddressCommand(addressCommand);
        return "redirect:/address/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/address/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/addresses";
    }
}
