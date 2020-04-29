package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.example.realestate.services.StorageService;

@Controller
public class StorageController {

    private final StorageService service;

    public StorageController(StorageService service) {
        this.service = service;
    }

    @RequestMapping("/storages/show/{id}")
    public String getStorageById(@PathVariable String id, Model model) {
        model.addAttribute("storage", service.getById(Long.valueOf(id)));
        return "storages/show";
    }

    @RequestMapping("/storages")
    public String getAllStorages(Model model) {
        model.addAttribute("storages", service.getStorages());
        return "storage";
    }
}
