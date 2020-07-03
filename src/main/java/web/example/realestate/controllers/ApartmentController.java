package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ApartmentService;
import web.example.realestate.services.ClientService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ClientService clientService;

    public ApartmentController(ApartmentService apartmentService, ClientService clientService) {
        this.apartmentService = apartmentService;
        this.clientService = clientService;
    }

    @GetMapping("/apartment/{id}/show")
    public String getApartmentById(@PathVariable String id, Model model) {
        model.addAttribute("apartment", apartmentService.getById(Long.valueOf(id)));
        return "apartment/show";
    }

    @GetMapping("/apartment")
    public String getAllApartments(Model model) {
        model.addAttribute("apartments", apartmentService.getApartments());
        return "apartments";
    }

    @GetMapping("/apartment/new")
    public String newApartment(Model model) {
        model.addAttribute("apartment", new FacilityCommand());
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentEmptyForm";
    }

    @GetMapping("/apartment/{id}/update")
    public String updateApartment(@PathVariable String id, Model model) {
        model.addAttribute("apartment", apartmentService.findCommandById(Long.valueOf(id)));
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentForm";
    }

    @PostMapping("/apartment/save")
    public String saveNew(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = apartmentService.saveDetached(command);
        return "redirect:/apartment/" + savedCommand.getId() + "/show";
    }

    @PostMapping("/apartment/update")
    public String updateExisting(@ModelAttribute FacilityCommand command) {
        FacilityCommand savedCommand = apartmentService.saveAttached(command);
        return "redirect:/apartment/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/apartment/{id}/delete")
    public String deleteById(@PathVariable String id) {
        apartmentService.deleteById(Long.valueOf(id));
        return "redirect:/apartment";
    }

    @GetMapping("/apartment/{id}/image")
    public String apartmentImageUpload(@PathVariable String id, Model model) {
        model.addAttribute("apartment", apartmentService.getById(Long.valueOf(id)));
        return "apartment/apartmentImageUpload";
    }

    @GetMapping("apartment/{id}/apartmentimage")
    public void renderApartmentImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        FacilityCommand facilityCommand = apartmentService.findCommandById(Long.valueOf(id));

        if (facilityCommand.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(facilityCommand.getImage());
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @PostMapping("/apartment/{id}/image")
    public String saveApartmentImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        apartmentService.saveImage(Long.valueOf(id), file);
        return "redirect:/apartment/" + id + "/show";
    }
}
