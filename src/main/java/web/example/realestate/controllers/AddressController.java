package web.example.realestate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.services.AddressService;

import javax.validation.Valid;

@Controller
public class AddressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
        LOGGER.info("New instance of AddressController created.");
    }

    @GetMapping("/address/{id}/show")
    public String getAddressById(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'getAddressById(@PathVariable String id, Model model)' method");

        model.addAttribute("address", service.getById(Long.valueOf(id)));
        LOGGER.debug("add address attribute to model");

        LOGGER.trace("'getAddressById(@PathVariable String id, Model model)' executed successfully.");
        return "address/show";
    }

    @GetMapping("/addresses")
    public String getAddresses(Model model) {
        LOGGER.trace("Enter in 'getAddresses(Model model)' method");

        model.addAttribute("addresses", service.getAddresses());
        LOGGER.debug("add addresses attribute to model");

        LOGGER.trace("'getAddresses(Model model)' executed successfully.");
        return "address";
    }

    @GetMapping("/address/{id}/update")
    public String updateAddress(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'updateAddress(@PathVariable String id, Model model)' method");

        model.addAttribute("address", service.findCommandById(Long.valueOf(id)));
        LOGGER.debug("add addresses attribute to model");

        LOGGER.trace("'updateAddress(@PathVariable String id, Model model)' executed successfully.");
        return "address/addressForm";
    }

    @PostMapping("/address")
    public String saveOrUpdate(@Valid @ModelAttribute("address") AddressCommand addressCommand,
                               BindingResult bindingResult, Model model) {
        LOGGER.trace("Enter in 'saveOrUpdate(AddressCommand addressCommand, BindingResult bindingResult, Model model)' method");

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("address", addressCommand);
            LOGGER.debug("add address attribute to model");

            return "address/addressForm";
        }
        AddressCommand savedCommand = service.saveAddressCommand(addressCommand);

        LOGGER.trace("'saveOrUpdate(AddressCommand addressCommand, BindingResult bindingResult, Model model)' executed successfully.");
        return "redirect:/address/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/address/{id}/delete")
    public String deleteById(@PathVariable String id) {
        LOGGER.trace("Enter in 'deleteById(@PathVariable String id)' method");

        service.deleteById(Long.valueOf(id));

        LOGGER.trace("'deleteById(@PathVariable String id)' executed successfully.");
        return "redirect:/addresses";
    }
}
