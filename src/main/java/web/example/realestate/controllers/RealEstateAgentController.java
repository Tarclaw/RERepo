package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.RealEstateAgentService;

@Controller
public class RealEstateAgentController {

    private final RealEstateAgentService service;

    public RealEstateAgentController(RealEstateAgentService service) {
        this.service = service;
    }

    @RequestMapping("/realEstateAgents/show/{id}")
    public String getRealEstateAgentById(@PathVariable String id, Model model) {
        model.addAttribute("realEstateAgent", service.getById(Long.valueOf(id)));
        return "realEstateAgents/show";
    }

    @RequestMapping("/realEstateAgents")
    public String getAllRealEstateAgents(Model model) {
        model.addAttribute("realEstateAgents", service.getRealEstateAgents());
        return "realEstateAgent";
    }
}
