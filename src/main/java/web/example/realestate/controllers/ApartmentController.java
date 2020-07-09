package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.services.ApartmentService;
import web.example.realestate.services.ClientService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
        model.addAttribute("address", new AddressCommand());
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentEmptyForm";
    }

    @GetMapping("/apartment/{id}/update")
    public String updateApartment(@PathVariable String id, Model model) {
        FacilityCommand apartment = apartmentService.findCommandById(Long.valueOf(id));
        model.addAttribute("apartment", apartment);
        model.addAttribute("address", apartment.getAddress());
        model.addAttribute("clients", clientService.getClients());
        return "apartment/apartmentForm";
    }

    @PostMapping("/apartment/save")
    public String saveNew(@Valid @ModelAttribute("apartment") FacilityCommand apartmentCommand, BindingResult apartmentBinding,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding, Model model) {

        if (apartmentBinding.hasErrors() || addressBinding.hasErrors()) {

            apartmentBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("apartment", apartmentCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("clients", clientService.getClients());

            return "apartment/apartmentEmptyForm";
        }

        apartmentCommand.setAddress(addressCommand);
        FacilityCommand savedApartment = apartmentService.saveDetached(apartmentCommand);

        return "redirect:/apartment/" + savedApartment.getId() + "/show";
    }

    @PostMapping("/apartment/update")
    public String updateExisting(@Valid @ModelAttribute("apartment") FacilityCommand apartmentCommand, BindingResult apartmentBinding,
                                 @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding, Model model) {

        if (apartmentBinding.hasErrors() || addressBinding.hasErrors()) {

            apartmentBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("apartment", apartmentCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("clients", clientService.getClients());

            return "apartment/apartmentForm";
        }

        apartmentCommand.setAddress(addressCommand);
        FacilityCommand savedApartment = apartmentService.saveAttached(apartmentCommand);

        return "redirect:/apartment/" + savedApartment.getId() + "/show";
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
        FacilityCommand apartment = apartmentService.findCommandById(Long.valueOf(id));

        if (apartment.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(apartment.getImage());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @PostMapping("/apartment/{id}/image")
    public String saveApartmentImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        apartmentService.saveImage(Long.valueOf(id), file);
        return "redirect:/apartment/" + id + "/show";
    }
}
