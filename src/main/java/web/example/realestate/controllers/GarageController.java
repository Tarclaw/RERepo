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
import web.example.realestate.services.GarageService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class GarageController {

    private final GarageService garageService;
    private final ClientService clientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageController.class);

    public GarageController(GarageService garageService, ClientService clientService) {
        this.garageService = garageService;
        this.clientService = clientService;
        LOGGER.info("New instance of GarageController created.");
    }

    @GetMapping("/garage/{id}/show")
    public String getGarageById(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'getGarageById(@PathVariable String id, Model model)' method");

        model.addAttribute("garage", garageService.getById(Long.valueOf(id)));
        LOGGER.debug("add garage attribute to model");

        LOGGER.trace("'getGarageById(@PathVariable String id, Model model)' executed successfully.");
        return "garage/show";
    }

    @GetMapping("/garage")
    public String getAllGarages(Model model) {
        LOGGER.trace("Enter in 'getAllGarages(Model model)' method");

        model.addAttribute("garages", garageService.getGarages());
        LOGGER.debug("add garages attribute to model");

        LOGGER.trace("'getAllGarages(Model model)' executed successfully.");
        return "garages";
    }

    @GetMapping("/garage/new")
    public String newGarage(Model model) {
        LOGGER.trace("Enter in 'getAllGarages(Model model)' method");

        model.addAttribute("garage", new FacilityCommand());
        LOGGER.debug("add garage attribute to model");

        model.addAttribute("address", new AddressCommand());
        LOGGER.debug("add address attribute to model");

        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'getAllGarages(Model model)' executed successfully.");
        return "garage/garageEmptyForm";
    }

    @GetMapping("/garage/{id}/update")
    public String updateGarage(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'updateGarage(@PathVariable String id, Model model)' method");

        FacilityCommand garage = garageService.findCommandById(Long.valueOf(id));
        model.addAttribute("garage", garage);
        LOGGER.debug("add garage attribute to model");

        model.addAttribute("address", garage.getAddress());
        LOGGER.debug("add address attribute to model");

        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'updateGarage(@PathVariable String id, Model model)' executed successfully.");
        return "garage/garageForm";
    }

    @PostMapping("/garage/save")
    public String saveNew(@Valid @ModelAttribute("garage") FacilityCommand garageCommand, BindingResult garageBinding,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding,
                          Model model) {
        LOGGER.trace("Enter in 'saveNew(garageCommand, garageBinding, addressCommand, addressBinding, Model model' method");

        if (garageBinding.hasErrors() || addressBinding.hasErrors()) {

            garageBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("garage", garageCommand);
            LOGGER.debug("add garage attribute to model");

            model.addAttribute("address", addressCommand);
            LOGGER.debug("add address attribute to model");

            model.addAttribute("clients", clientService.getClients());
            LOGGER.debug("add clients attribute to model");

            return "garage/garageEmptyForm";
        }

        garageCommand.setAddress(addressCommand);
        FacilityCommand savedGarage = garageService.saveDetached(garageCommand);

        LOGGER.trace("'saveNew(garageCommand, garageBinding, addressCommand, addressBinding, Model model' executed successfully.");
        return "redirect:/garage/" + savedGarage.getId() + "/show";
    }

    @PostMapping("/garage/update")
    public String updateExisting(@Valid @ModelAttribute("garage") FacilityCommand garageCommand, BindingResult garageBinding,
                                 @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding,
                                 Model model) {
        LOGGER.trace("Enter in 'updateExisting(garageCommand, garageBinding, addressCommand, addressBinding, Model model' method");

        if (garageBinding.hasErrors() || addressBinding.hasErrors()) {

            garageBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("garage", garageCommand);
            LOGGER.debug("add garage attribute to model");

            model.addAttribute("address", addressCommand);
            LOGGER.debug("add address attribute to model");

            model.addAttribute("clients", clientService.getClients());
            LOGGER.debug("add clients attribute to model");

            return "garage/garageForm";
        }

        garageCommand.setAddress(addressCommand);
        FacilityCommand savedGarage = garageService.saveAttached(garageCommand);

        LOGGER.trace("'updateExisting(garageCommand, garageBinding, addressCommand, addressBinding, Model model' executed successfully.");
        return "redirect:/garage/" + savedGarage.getId() + "/show";
    }

    @GetMapping("/garage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        LOGGER.trace("Enter in 'deleteById(@PathVariable String id)' method");

        garageService.deleteById(Long.valueOf(id));

        LOGGER.trace("'deleteById(@PathVariable String id)' executed successfully.");
        return "redirect:/garage";
    }

    @GetMapping("/garage/{id}/image")
    public String garageImageUpload(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'garageImageUpload(@PathVariable String id, Model model)' method");

        model.addAttribute("garage", garageService.getById(Long.valueOf(id)));
        LOGGER.debug("add garage attribute to model");

        LOGGER.trace("'garageImageUpload(@PathVariable String id, Model model)' executed successfully.");
        return "garage/garageImageUpload";
    }

    @GetMapping("/garage/{id}/garageimage")
    public void renderGarageImage(@PathVariable String id, HttpServletResponse response) {
        LOGGER.trace("Enter in 'renderGarageImage(@PathVariable String id, HttpServletResponse response)' method");

        FacilityCommand garage = garageService.findCommandById(Long.valueOf(id));

        if (garage.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(garage.getImage());
            try {
                IOUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                LOGGER.error("Attention can't render garage image!!!", e);
                throw new ImageCorruptedException(e.getMessage());
            }
        }

        LOGGER.trace("'renderGarageImage(@PathVariable String id, HttpServletResponse response)' executed successfully.");
    }

    @PostMapping("/garage/{id}/image")
    public String saveGarageImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
        LOGGER.trace("Enter in 'saveGarageImage(String id, MultipartFile multipartFile)' method");

        garageService.saveImage(Long.valueOf(id), multipartFile);

        LOGGER.trace("'saveGarageImage(String id, MultipartFile multipartFile)' executed successfully.");
        return "redirect:/garage/" + id + "/show";
    }
}
