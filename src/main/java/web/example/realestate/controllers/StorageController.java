package web.example.realestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.StorageService;

@Controller
public class StorageController {

    private final StorageService storageService;
    private final ClientService clientService;

    public StorageController(StorageService storageService, ClientService clientService) {
        this.storageService = storageService;
        this.clientService = clientService;
    }

    @GetMapping("/storage/{id}/show")
    public String getStorageById(@PathVariable String id, Model model) {
        model.addAttribute("storage", storageService.getById(Long.valueOf(id)));
        return "storage/show";
    }

    @GetMapping("/storage")
    public String getAllStorages(Model model) {
        model.addAttribute("storages", storageService.getStorages());
        return "storages";
    }

    @GetMapping("/storage/new")
    public String newStorage(Model model) {
        model.addAttribute("storage", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "storage/storageEmptyForm";
    }

    @GetMapping("/storage/{id}/update")
    public String updateStorage(@PathVariable String id, Model model) {
        model.addAttribute("storage", storageService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "storage/storageForm";
    }

    @PostMapping("/storage/save")
    public String saveNew(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = storageService.saveDetached(command);
        return "redirect:/storage/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/storage/update")
    public String updateExisting(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = storageService.saveAttached(command);
        return "redirect:/storage/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/storage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        storageService.deleteById(Long.valueOf(id));
        return "redirect:/storage";
    }
}
