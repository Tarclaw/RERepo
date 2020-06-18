package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.services.FacilityService;
import web.example.realestate.services.MappingService;

import java.util.List;

@Controller
public class FacilityController {

    private final FacilityService facilityService;
    private final MappingService mappingService;

    public FacilityController(FacilityService facilityService, MappingService mappingService) {
        this.facilityService = facilityService;
        this.mappingService = mappingService;
    }

    @GetMapping({"", "/", "/facilities"})
    public String getAllFacilities(Model model) {
        List<Facility> facilities = facilityService.getFacilities();
        model.addAttribute("facilities", facilities);
        model.addAttribute("mappings", mappingService.buildMapping(facilities));
        return "facility";
    }
}
