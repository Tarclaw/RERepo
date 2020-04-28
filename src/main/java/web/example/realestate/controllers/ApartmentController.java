package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.ApartmentService;

@Controller
public class ApartmentController {

    private final ApartmentService service;

    public ApartmentController(ApartmentService service) {
        this.service = service;
    }

    @RequestMapping("/apartments/show/{id}")
    public String getApartmentById(@PathVariable String id, Model model) {
        model.addAttribute("apartment", service.getById(Long.valueOf(id)));
        return "apartments/show";
    }

    @RequestMapping("/apartments")
    public String getAllApartments(Model model) {
        model.addAttribute("apartments", service.getApartments());
        return "apartments";
    }
}
