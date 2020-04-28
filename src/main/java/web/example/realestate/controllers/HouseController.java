package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.HouseService;

@Controller
public class HouseController {

    private final HouseService service;

    public HouseController(HouseService service) {
        this.service = service;
    }

    @RequestMapping("/houses/show/{id}")
    public String getHouseById(@PathVariable String id, Model model) {
        model.addAttribute("house", service.getById(Long.valueOf(id)));
        return "houses/show";
    }

    @RequestMapping("/houses")
    public String getAllHouses(Model model) {
        model.addAttribute("houses", service.getHouses());
        return "houses";
    }
}
