package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.FacilityObjectService;

@Controller
public class FacilityObjectController {

    private final FacilityObjectService service;

    public FacilityObjectController(FacilityObjectService service) {
        this.service = service;
    }

    @RequestMapping("/facilityObjects/show/{id}")
    public String getFacilityObjectById(@PathVariable String id, Model model) {
        model.addAttribute("facilityObject", service.getById(Long.valueOf(id)));
        return "facilityObject/show";
    }

    @RequestMapping("/facilityObjects")
    public String getAllFacilityObjects(Model model) {
        model.addAttribute("facilityObjects", service.getFacilityObjects());
        return "facilityObjects";
    }
}
