package web.example.realestate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FacilityController.class);

    public FacilityController(FacilityService facilityService, MappingService mappingService) {
        this.facilityService = facilityService;
        this.mappingService = mappingService;
        LOGGER.info("New instance of FacilityController created.");
    }

    @GetMapping({"", "/", "/facilities"})
    public String getAllFacilities(Model model) {
        LOGGER.trace("Enter in 'getAllFacilities(Model model)' method");

        List<Facility> facilities = facilityService.getFacilities();
        model.addAttribute("facilities", facilities);
        LOGGER.debug("added facilities attribute to model.");

        model.addAttribute("mappings", mappingService.buildMapping(facilities));
        LOGGER.debug("added mappings attribute to model.");

        LOGGER.trace("getAllFacilities(Model model)' executed successfully.");
        return "facility";
    }

    @GetMapping("/facility/{id}/delete")
    public String deleteFacility(@PathVariable String id) {
        LOGGER.trace("Enter in 'deleteFacility(@PathVariable String id)' method");

        facilityService.deleteById(Long.valueOf(id));

        LOGGER.trace("'deleteFacility(@PathVariable String id)' executed successfully");
        return "redirect:/facilities";
    }
}
