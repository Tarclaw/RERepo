package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.HouseService;

@Controller
public class HouseController {

    private final HouseService houseService;
    private final ClientService clientService;

    public HouseController(HouseService houseService, ClientService clientService) {
        this.houseService = houseService;
        this.clientService = clientService;
    }

    @GetMapping("/house/{id}/show")
    public String getHouseById(@PathVariable String id, Model model) {
        model.addAttribute("house", houseService.getById(Long.valueOf(id)));
        return "house/show";
    }

    @GetMapping("/house")
    public String getAllHouses(Model model) {
        model.addAttribute("houses", houseService.getHouses());
        return "houses";
    }

    @GetMapping("/house/new")
    public String newHouse(Model model) {
        model.addAttribute("house", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "house/houseEmptyForm";
    }

    @GetMapping("/house/{id}/update")
    public String updateHouse(@PathVariable String id, Model model) {
        model.addAttribute("house", houseService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "house/houseForm";
    }

    @PostMapping("/house/save")
    public String saveNew(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = houseService.saveDetached(command);
        return "redirect:/house/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/house/update")
    public String updateExisting(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = houseService.saveAttached(command);
        return "redirect:/house/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/house/{id}/delete")
    public String deleteById(@PathVariable String id) {
        houseService.deleteById(Long.valueOf(id));
        return "redirect:/house";
    }
}
