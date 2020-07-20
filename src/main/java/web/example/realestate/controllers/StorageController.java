package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.exceptions.ImageCorruptedException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);

    public StorageController(StorageService storageService, ClientService clientService) {
        this.storageService = storageService;
        this.clientService = clientService;
        LOGGER.info("New instance of StorageController created.");
    }

    @GetMapping("/storage/{id}/show")
    public String getStorageById(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'getStorageById(@PathVariable String id, Model model)' method");

        model.addAttribute("storage", storageService.getById(Long.valueOf(id)));
        LOGGER.debug("add storage attribute to model");

        LOGGER.trace("'getStorageById(@PathVariable String id, Model model)' executed successfully.");
        return "storage/show";
    }

    @GetMapping("/storage")
    public String getAllStorages(Model model) {
        LOGGER.trace("Enter in 'getAllStorages(Model model)' method");

        model.addAttribute("storages", storageService.getStorages());
        LOGGER.debug("add storages attribute to model");

        LOGGER.trace("'getAllStorages(Model model)' executed successfully.");
        return "storages";
    }

    @GetMapping("/storage/new")
    public String newStorage(Model model) {
        LOGGER.trace("Enter in 'newStorage(Model model)' method");

        model.addAttribute("storage", new FacilityCommand());
        LOGGER.debug("add storage attribute to model");

        model.addAttribute("address", new AddressCommand());
        LOGGER.debug("add address attribute to model");

        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'newStorage(Model model)' executed successfully.");
        return "storage/storageEmptyForm";
    }

    @GetMapping("/storage/{id}/update")
    public String updateStorage(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'updateStorage(@PathVariable String id, Model model)' method");

        FacilityCommand storage = storageService.findCommandById(Long.valueOf(id));

        model.addAttribute("storage", storage);
        LOGGER.debug("add storage attribute to model");

        model.addAttribute("address", storage.getAddress());
        LOGGER.debug("add address attribute to model");

        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'updateStorage(@PathVariable String id, Model model)' executed successfully.");
        return "storage/storageForm";
    }

    @PostMapping("/storage/save")
    public String saveNew(@Valid @ModelAttribute("storage") FacilityCommand storageCommand, BindingResult storageBinding,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding,
                          Model model) {
        LOGGER.trace("Enter in 'saveNew(storageCommand, storageBinding, addressCommand, addressBinding, model)' method");

        if (storageBinding.hasErrors() || addressBinding.hasErrors()) {

            storageBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("storage", storageCommand);
            LOGGER.debug("add storage attribute to model");

            model.addAttribute("address", addressCommand);
            LOGGER.debug("add address attribute to model");

            model.addAttribute("clients", clientService.getClients());
            LOGGER.debug("add clients attribute to model");

            return "storage/storageEmptyForm";
        }

        storageCommand.setAddress(addressCommand);
        FacilityCommand savedStorage = storageService.saveDetached(storageCommand);

        LOGGER.trace("'saveNew(storageCommand, storageBinding, addressCommand, addressBinding, model)' executed successfully.");
        return "redirect:/storage/" + savedStorage.getId() + "/show";
    }

    @PostMapping("/storage/update")
    public String updateExisting(@Valid @ModelAttribute("storage") FacilityCommand storageCommand, BindingResult storageBinding,
                                 @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding,
                                 Model model) {
        LOGGER.trace("Enter in 'updateExisting(storageCommand, storageBinding, addressCommand, addressBinding, model)' method");

        if (storageBinding.hasErrors() || addressBinding.hasErrors()) {

            storageBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("storage", storageCommand);
            LOGGER.debug("add storage attribute to model");

            model.addAttribute("address", addressCommand);
            LOGGER.debug("add address attribute to model");

            model.addAttribute("clients", clientService.getClients());
            LOGGER.debug("add clients attribute to model");

            return "storage/storageForm";
        }

        storageCommand.setAddress(addressCommand);
        FacilityCommand savedStorage = storageService.saveAttached(storageCommand);

        LOGGER.trace("'updateExisting(storageCommand, storageBinding, addressCommand, addressBinding, model)' executed successfully.");
        return "redirect:/storage/" + savedStorage.getId() + "/show";
    }

    @GetMapping("/storage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        LOGGER.trace("Enter in 'deleteById(@PathVariable String id)' method");

        storageService.deleteById(Long.valueOf(id));

        LOGGER.trace("'deleteById(@PathVariable String id)' executed successfully.");
        return "redirect:/storage";
    }

    @GetMapping("/storage/{id}/image")
    public String storageImageUpload(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'storageImageUpload(@PathVariable String id, Model model)' method");

        model.addAttribute("storage", storageService.getById(Long.valueOf(id)));
        LOGGER.debug("add storage attribute to model");

        LOGGER.trace("'storageImageUpload(@PathVariable String id, Model model)' executed successfully.");
        return "storage/storageImageUpload";
    }

    @GetMapping("/storage/{id}/storageimage")
    public void renderStorageImage(@PathVariable String id, HttpServletResponse response) {
        LOGGER.trace("Enter in 'renderStorageImage(@PathVariable String id, HttpServletResponse response' method");

        FacilityCommand storage = storageService.findCommandById(Long.valueOf(id));

        if (storage.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(storage.getImage());
            try {
                IOUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                LOGGER.error("Attention can't render storage image!!!", e);
                throw new ImageCorruptedException(e.getMessage());
            }
        }

        LOGGER.trace("'renderStorageImage(@PathVariable String id, HttpServletResponse response' executed successfully.");
    }

    @PostMapping("/storage/{id}/image")
    public String saveStorageImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
        LOGGER.trace("Enter in 'saveStorageImage(String id, MultipartFile multipartFile)' method");

        storageService.saveImage(Long.valueOf(id), multipartFile);

        LOGGER.trace("'saveStorageImage(String id, MultipartFile multipartFile)' executed successfully.");
        return "redirect:/storage/" + id + "/show";
    }
}
