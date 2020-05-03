package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.FacilityService;

@Controller
public class FacilityController {

    private final FacilityService service;

    public FacilityController(FacilityService service) {
        this.service = service;
    }

    @GetMapping
    @RequestMapping("/facilities/{id}/show")
    public String getFacilityById(@PathVariable String id, Model model) {
        model.addAttribute("facility", service.getById(Long.valueOf(id)));
        return "facilities/show";
    }

    @RequestMapping("/facilities")
    public String getAllFacilities(Model model) {
        model.addAttribute("facilities", service.getFacilities());
        return "facility";
    }

    @GetMapping
    @RequestMapping("/facility/new")
    public String newFacility(Model model) {
        model.addAttribute("facility", new FacilityCommand());
        return "facility/facilityForm";
    }

    @GetMapping
    @RequestMapping("/facility/{id}/update")
    public String updateFacility(@PathVariable String id, Model model) {
        model.addAttribute("facility", service.findCommandById(Long.valueOf(id)));
        return "facility/facilityForm";
    }

    @PostMapping("/facility")
    public String saveOrUpdate(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = service.saveFacilityCommand(command);
        return "redirect:/facility/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/facilities/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
