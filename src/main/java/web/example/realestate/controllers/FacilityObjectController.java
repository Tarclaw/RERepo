package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.services.FacilityObjectService;

@Controller
public class FacilityObjectController {

    private final FacilityObjectService service;

    public FacilityObjectController(FacilityObjectService service) {
        this.service = service;
    }

    @GetMapping("/facilityObject/{id}/show")
    public String getFacilityObjectById(@PathVariable String id, Model model) {
        model.addAttribute("facilityObject", service.getById(Long.valueOf(id)));
        return "facilityObject/show";
    }

    @GetMapping("/facilityObjects")
    public String getAllFacilityObjects(Model model) {
        model.addAttribute("facilityObjects", service.getFacilityObjects());
        return "facilityObject";
    }

    @GetMapping("/facilityObject/new")
    public String newFacilityObject(Model model) {
        model.addAttribute("facilityObject", new FacilityObjectCommand());
        return "facilityObject/facilityObjectForm";
    }

    @GetMapping("/facilityObject/{id}/update")
    public String updateFacilityObject(@PathVariable String id, Model model) {
        model.addAttribute("facilityObject", service.findCommandById(Long.valueOf(id)));
        return "facilityObject/facilityObjectForm";
    }

    @PostMapping("/facilityObject")
    public String saveOrUpdate(@ModelAttribute FacilityObjectCommand command) {
        FacilityObjectCommand savedCommand = service.saveFacilityObjectCommand(command);
        return "redirect:/facilityObject/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/facilityObject/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
