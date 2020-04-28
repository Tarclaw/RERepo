package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.FacilityService;

@Controller
public class FacilityController {

    private final FacilityService service;

    public FacilityController(FacilityService service) {
        this.service = service;
    }

    @RequestMapping("/facilities")
    public String getAllFacilities(Model model) {
        model.addAttribute("facilities", service.getFacilities());
        return "facilities";
    }
}
