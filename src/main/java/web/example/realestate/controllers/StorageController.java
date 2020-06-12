package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.StorageService;

@Controller
public class StorageController {

    private final StorageService service;

    public StorageController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/storage/{id}/show")
    public String getStorageById(@PathVariable String id, Model model) {
        model.addAttribute("storage", service.getById(Long.valueOf(id)));
        return "storage/show";
    }

    @GetMapping("/storages")
    public String getAllStorages(Model model) {
        model.addAttribute("storages", service.getStorages());
        return "storage";
    }

    @GetMapping("/storage/new")
    public String newStorage(Model model) {
        model.addAttribute("storage", new FacilityCommand());
        return "storage/storageForm";
    }

    @GetMapping("/storage/{id}/update")
    public String updateStorage(@PathVariable String id, Model model) {
        model.addAttribute("storage", service.findCommandById(Long.valueOf(id)));
        return "storage/storageForm";
    }

    @PostMapping("/storage")
    public String saveOrUpdate(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = service.saveStorageCommand(command);
        return "redirect:/storage/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/storage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/storages";
    }
}
