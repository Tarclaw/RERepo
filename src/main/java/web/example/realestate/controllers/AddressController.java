package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.AddressService;

@Controller
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @RequestMapping("/addresses/show/{id}")
    public String getAddressById(@PathVariable String id, Model model) {
        model.addAttribute("address", service.getById(Long.valueOf(id)));
        return "addresses/show";
    }

    @RequestMapping("/addresses")
    public String getAddresses(Model model) {
        model.addAttribute("addresses", service.getAddresses());
        return "addresses";
    }
}
