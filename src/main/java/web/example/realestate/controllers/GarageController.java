package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.GarageService;

@Controller
public class GarageController {

    private final GarageService service;

    public GarageController(GarageService service) {
        this.service = service;
    }

    @RequestMapping("/garages/show/{id}")
    public String getGarageById(@PathVariable String id, Model model) {
        model.addAttribute("garage", service.getById(Long.valueOf(id)));
        return "garages/show";
    }

    @RequestMapping("/garages")
    public String getAllGarages(Model model) {
        model.addAttribute("garages", service.getGarages());
        return "garage";
    }
}
