package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
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

    public GarageController(GarageService garageService, ClientService clientService) {
        this.garageService = garageService;
        this.clientService = clientService;
    }

    @GetMapping("/garage/{id}/show")
    public String getGarageById(@PathVariable String id, Model model) {
        model.addAttribute("garage", garageService.getById(Long.valueOf(id)));
        return "garage/show";
    }

    @GetMapping("/garage")
    public String getAllGarages(Model model) {
        model.addAttribute("garages", garageService.getGarages());
        return "garages";
    }

    @GetMapping("/garage/new")
    public String newGarage(Model model) {
        model.addAttribute("garage", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "garage/garageEmptyForm";
    }

    @GetMapping("/garage/{id}/update")
    public String updateGarage(@PathVariable String id, Model model) {
        model.addAttribute("garage", garageService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "garage/garageForm";
    }

    @PostMapping("/garage/save")
    public String saveNew(@Valid @ModelAttribute("garage") FacilityCommand command,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            return "garage/garageEmptyForm";
        }
        FacilityCommand savedCommand = garageService.saveDetached(command);
        return "redirect:/garage/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/garage/update")
    public String updateExisting(@Valid @ModelAttribute("garage") FacilityCommand command,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            return "garage/garageForm";
        }
        FacilityCommand savedCommand = garageService.saveAttached(command);
        return "redirect:/garage/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/garage/{id}/delete")
    public String deleteById(@PathVariable String id) {
        garageService.deleteById(Long.valueOf(id));
        return "redirect:/garage";
    }

    @GetMapping("/garage/{id}/image")
    public String garageImageUpload(@PathVariable String id, Model model) {
        model.addAttribute("garage", garageService.getById(Long.valueOf(id)));
        return "garage/garageImageUpload";
    }

    @GetMapping("/garage/{id}/garageimage")
    public void renderGarageImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        FacilityCommand garage = garageService.findCommandById(Long.valueOf(id));

        if (garage.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(garage.getImage());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @PostMapping("/garage/{id}/image")
    public String saveGarageImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
        garageService.saveImage(Long.valueOf(id), multipartFile);
        return "redirect:/garage/" + id + "/show";
    }
}
