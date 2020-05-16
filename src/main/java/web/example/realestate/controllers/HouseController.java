package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.HouseService;

@Controller
public class HouseController {

    private final HouseService service;

    public HouseController(HouseService service) {
        this.service = service;
    }

    @GetMapping("/house/{id}/show")
    public String getHouseById(@PathVariable String id, Model model) {
        model.addAttribute("house", service.getById(Long.valueOf(id)));
        return "house/show";
    }

    @GetMapping("/houses")
    public String getAllHouses(Model model) {
        model.addAttribute("houses", service.getHouses());
        return "house";
    }

    @GetMapping("/house/new")
    public String newHouse(Model model) {
        model.addAttribute("house", new FacilityCommand());
        return "house/houseForm";
    }

    @GetMapping("/house/{id}/update")
    public String updateHouse(@PathVariable String id, Model model) {
        model.addAttribute("house", service.findCommandById(Long.valueOf(id)));
        return "house/houseForm";
    }

    @PostMapping("/house")
    public String saveOrUpdate(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = service.saveHouseCommand(command);
        return "redirect:/house/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/house/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
