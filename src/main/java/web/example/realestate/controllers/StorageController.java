package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.StorageService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public String saveNew(@Valid @ModelAttribute("storage") FacilityCommand command,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            return "storage/storageEmptyForm";
        }
        FacilityCommand savedCommand = storageService.saveDetached(command);
        return "redirect:/storage/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/storage/update")
    public String updateExisting(@Valid @ModelAttribute("storage") FacilityCommand command,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            return "storage/storageForm";
        }
        FacilityCommand savedCommand = storageService.saveAttached(command);
        return "redirect:/storage/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/storage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        storageService.deleteById(Long.valueOf(id));
        return "redirect:/storage";
    }

    @GetMapping("/storage/{id}/image")
    public String storageImageUpload(@PathVariable String id, Model model) {
        model.addAttribute("storage", storageService.getById(Long.valueOf(id)));
        return "storage/storageImageUpload";
    }

    @GetMapping("/storage/{id}/storageimage")
    public void renderStorageImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        FacilityCommand storage = storageService.findCommandById(Long.valueOf(id));

        if (storage.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(storage.getImage());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @PostMapping("/storage/{id}/image")
    public String saveStorageImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
        storageService.saveImage(Long.valueOf(id), multipartFile);
        return "redirect:/storage/" + id + "/show";
    }
}
